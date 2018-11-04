package com.meandmyphone.genericdevicefancyvator.core.gl;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.meandmyphone.genericdevicefancyvator.core.background.GradientBackground;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ITransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;
import com.meandmyphone.genericdevicefancyvator.core.util.Mathf;
import com.meandmyphone.genericdevicefancyvator.json.Parser;
import com.meandmyphone.genericdevicefancyvator.json.ResourceExtractor;
import com.meandmyphone.genericdevicefancyvator.json.SpriteSorter;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.transformer.GDFTransformer;
import com.meandmyphone.genericdevicefancyvator.transformer.Transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.orthoM;

/**
 * Created by csumpakadabra on 2017.10.15..
 */

public class GLRenderer implements GLSurfaceView.Renderer {

    public static final int
            LANDSCAPE_MODE = 0xF0F0,
            PORTRAIT_MODE = 0xF0F1;

    static int screenWidth, screenHeight;

    public static final int FPS = 30;

    private final String TAG = "Renderer";
    private Scene scene;
    private int runMode = PORTRAIT_MODE;
    private final float[] projectionMatrix = new float[16];
    private Context context;
    private TextureShaderProgram textureProgram;
    private long lastFrameTime, lastOffsetChangeStartTime, lastOffsetChangeEndTime;
    private boolean offsetChanging, touchEnabled;
    private float currentXOffset, targetXOffset, startXOffset, maxOffset;

    GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Logger.Log(TAG, "Surface created!");
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        textureProgram = new TextureShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Logger.Log(TAG, "Surface changed! Width = %d, Height = %d", width, height);
        Projection projectionLandscape;
        Projection projectionPortrait;
        Projection projection;
        screenWidth = width;
        screenHeight = height;

        glViewport(0, 0, width, height);

        float [] projectionMatrixLandscape = new float[16];
        float [] projectionMatrixPortrait = new float[16];

        // TODO separate portait - landscape
        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;

        orthoM(projectionMatrixLandscape, 0, -aspectRatio, aspectRatio, -1f, 1f, 0, 10f);
        projectionLandscape = new Projection(projectionMatrixLandscape, width, height);
        orthoM(projectionMatrixPortrait, 0, -1f, 1f, -aspectRatio, aspectRatio, 0f, 10f);
        projectionPortrait = new Projection(projectionMatrixPortrait, width, height);

        if (width > height) {
            runMode = LANDSCAPE_MODE;
            System.arraycopy(projectionMatrixLandscape, 0, projectionMatrix, 0, projectionMatrixLandscape.length);
            projection = projectionLandscape;
            Logger.Log(TAG, "Renderer running in LANDSCAPE mode.");
        } else {
            runMode = PORTRAIT_MODE;
            System.arraycopy(projectionMatrixPortrait, 0, projectionMatrix, 0, projectionMatrixPortrait.length);
            projection = projectionPortrait;
            Logger.Log(TAG, "Renderer running in PORTRAIT mode.");
        }
        Logger.Log(TAG, "aspectratio = %f", aspectRatio);

        try {
            int input = context.getResources().getIdentifier("input", "raw", context.getPackageName());
            Parser parser = new Parser();
            com.meandmyphone.genericdevicefancyvator.json.pojo.Scene xmlScene =
                    parser.parse(context.getResources().openRawResource(input));
            ResourceExtractor resourceExtractor = new ResourceExtractor(xmlScene, context);
            scene = new Scene(context, runMode, projection, resourceExtractor.extractResources());
            Transformer transformer = new GDFTransformer(context, this, xmlScene, scene);
            SpriteSorter spriteSorter = new SpriteSorter(xmlScene.getSprites());
            List<SpriteFactory.Sprite> sprites = new ArrayList<>();
            for (Sprite s : spriteSorter.sortByDependents()) {
                SpriteFactory.Sprite sprite = transformer.transform(s);
                sprites.add(sprite);
                for (int i = 0; i <= sprite.transitions.size(); i++) {
                    ITransition transition = sprite.transitions.get(sprite.transitions.keyAt(i));
                    if (transition != null) {
                        transition.start();
                    }
                }
            }

            scene.sortSprites();
            maxOffset = 0.5f * (scene.getSceneWidth() - projection.getProjectionWidth());
        } catch (JsonSyntaxException | JsonIOException jsonException) {
            Toast.makeText(context, String.format("Invalid input.json: %s", jsonException.getMessage()), Toast.LENGTH_LONG).show();
        }

        scene.setBackground(new GradientBackground(context, Color.YELLOW, Color.BLUE, Color.BLUE, Color.BLUE));
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        limitFrameRate();
        calculateCameraOffset();

        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        if (scene.getBackground() != null) {
            scene.getBackground().draw();
        }

        for (SpriteFactory.Sprite sprite : scene.sprites_) {
            for (int transitionIndex = 0; transitionIndex < sprite.transitions.size(); transitionIndex++) {
                ITransition transition = sprite.transitions.get(sprite.transitions.keyAt(transitionIndex));
                transition.transit();
            }

            float[] transform = new float[16];
            float[] temp = new float[16];
            float[] rotation = new float[16];
            float[] pivot = new float[16];
            float[] translate = new float[16];
            float[] scale = new float[16];

            // ROTATION AND SCALE
            Matrix.setIdentityM(scale, 0); // Scale to identity
            Matrix.scaleM(scale, 0, sprite.scaleX, sprite.scaleY, 1); // Set scale
            Matrix.setIdentityM(transform, 0); // transform to identity
            Matrix.setIdentityM(pivot, 0); // pivot to identity
            Matrix.translateM(pivot, 0, pivot, 0, sprite.pivotX, sprite.pivotY, 0); // translate pivot matrix to pivot
            Matrix.setRotateEulerM(rotation, 0, 0, 0, sprite.angle); // euler rotate rotate matrix ...
            Matrix.multiplyMM(rotation, 0, pivot, 0, rotation, 0); // ... around pivot
            Matrix.multiplyMM(scale, 0, pivot, 0, scale, 0); // scale on pivot
            Matrix.setIdentityM(pivot, 0); // ???
            Matrix.translateM(pivot, 0, pivot, 0, -sprite.pivotX, -sprite.pivotY, 0); // TRANSLATE TO ORIGINAL
            Matrix.multiplyMM(rotation, 0, rotation, 0, pivot, 0); // CALCULATE ROTATION
            Matrix.multiplyMM(scale, 0, scale, 0, pivot, 0); // CALCULATE SCALE

            //TRANSLATE
            Matrix.setIdentityM(translate, 0);
            Matrix.translateM(translate, 0, sprite.translateX, sprite.translateY, 0);

            //MULTIPLY
            Matrix.multiplyMM(transform, 0, scale, 0, rotation, 0);
            Matrix.multiplyMM(transform, 0, translate, 0, transform, 0); // SCALE * ROTATION * TRANSLATE

            //FINAL MATRIX - PROJECT
            Matrix.multiplyMM(temp, 0, projectionMatrix, 0, transform, 0);

            textureProgram.useProgram();
            textureProgram.setUniforms(temp, scene.getTexture(sprite.getResourceId()), sprite.alpha);
            sprite.bindData(textureProgram);
            sprite.draw();
        }

        scene.onFrameDrawn();
    }

    private void calculateCameraOffset() {
        if (offsetChanging) {
            float xOffset = Ease.calculateFloat(ITransition.SINEASEINOUT, System.currentTimeMillis() - lastOffsetChangeStartTime, startXOffset, targetXOffset - startXOffset, 500);
            if (Math.abs(currentXOffset - targetXOffset) < 0.01) {
                offsetChanging = false;
                lastOffsetChangeEndTime = System.currentTimeMillis();
            }
            Matrix.translateM(projectionMatrix, 0, (xOffset - currentXOffset) * maxOffset, 0, 0);
            currentXOffset = xOffset;
            Logger.Log(TAG, "Current xOffset = %f", currentXOffset);
        } else {
            if (System.currentTimeMillis() - lastOffsetChangeEndTime > 3000) { // TODO don't hardcode!
                if (Math.abs(currentXOffset) > 0.01) {
                    setOffsetDelta(-1 * Math.signum(currentXOffset) * 0.5f);
                }
            }
        }
    }

    private void limitFrameRate() {
        long timeElapsed = System.currentTimeMillis() - lastFrameTime;
        long sleepTime = 1000 / FPS - timeElapsed;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (sleepTime < 0) {
            Logger.Log(TAG, "Warning: FPS drop, engine could not perform at requested speed = %d, latency = %d ms.", FPS, Math.abs(sleepTime));
        }
        lastFrameTime = System.currentTimeMillis();
    }

    public void setOffsetDelta(float offsetDelta) {
        if (offsetChanging) return;
        float newOffset = Mathf.clamp(targetXOffset + offsetDelta, -1, 1);
        if (newOffset < targetXOffset || newOffset > targetXOffset) {
            targetXOffset = newOffset;
            startXOffset = currentXOffset;
            lastOffsetChangeStartTime = System.currentTimeMillis();
            offsetChanging = true;
            // TODO handle offset change!
        }
    }

    public int getRunMode() {
        return runMode;
    }

    public Scene getCurrentScene() {
        return scene;
    }

    public void propagateTouchEvent(float screenX, float screenY) {
        if (touchEnabled) {
            Point2D touch = SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, SpaceConverter.screenToWorldPoint(screenX, screenY));
            // TODO handle touch!
        }
    }

    public int getFPS() {
        return FPS;
    }

    public boolean isTouchEnabled() {
        return touchEnabled;
    }

    public void setTouchEnabled(boolean touchEnabled) {
        this.touchEnabled = touchEnabled;
    }

    public static class SpaceConverter {
        public static Point2D worldToProjectionSpacePoint(float[] projectionMatrix, Point2D worldPoint) {
            float[] inverted = new float[16];
            Matrix.invertM(inverted, 0, projectionMatrix, 0);
            float[] p = new float[]{worldPoint.X, worldPoint.Y, 0, 1};
            float[] result = new float[4];
            Matrix.multiplyMV(result, 0, inverted, 0, p, 0);
            Point2D out = new Point2D(result[0], result[1]);
            out.X /= result[3];
            out.Y /= result[3];
            return out;
        }

        public static Point2D screenToWorldPoint(float x, float y) {
            final float screenX = (x / screenWidth) * 2 - 1;
            final float screenY = -((y / screenHeight) * 2 - 1);
            return new Point2D(screenX, screenY);
        }
    }
}
