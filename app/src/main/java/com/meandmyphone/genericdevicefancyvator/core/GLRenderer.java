package com.meandmyphone.genericdevicefancyvator.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.ITransition;
import com.meandmyphone.genericdevicefancyvator.util.Logger;
import com.meandmyphone.genericdevicefancyvator.util.Mathf;

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

    private final String TAG = "Renderer";
    private int FPS = 30, ghostAnimationValue = 2, LWPSpeed=3;
    private LWPTheme theme;
    private Scene scene;
    private Point2D projectionTopLeft, projectionBotRight;
    private static float projectionWidth, projectionHeight;
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
        theme = ThemeBuilder.newBuilder(context, this).build();
        theme.init(LWPSpeed,ghostAnimationValue); // TODO generifiy! map <string, value>
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        textureProgram = new TextureShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Logger.Log(TAG, "Surface changed! Width = %d, Height = %d", width, height);
        glViewport(0, 0, width, height);
//        perspectiveM(projectionMatrix, 0, 45, (float) width
//                / (float) height, 1f, 10f);

        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if (width > height) {
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0, 10f);
            runMode = LANDSCAPE_MODE;
            Logger.Log(TAG, "Renderer running in LANDSCAPE mode.");
        } else {
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, 0f, 10f);
            runMode = PORTRAIT_MODE;
            Logger.Log(TAG, "Renderer running in PORTRAIT mode.");
        }

        Logger.Log(TAG, "aspectratio = %f", aspectRatio);

        screenWidth = width;
        screenHeight = height;
        projectionTopLeft = SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, SpaceConverter.screenToWorldPoint(0, 0));
        projectionBotRight = SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, SpaceConverter.screenToWorldPoint(width, height));
        scene = new Scene(context, runMode, projectionTopLeft, projectionBotRight, theme);
        projectionWidth = projectionBotRight.X - projectionTopLeft.X;
        projectionHeight = projectionTopLeft.Y - projectionBotRight.Y;

        theme.fillScene(scene);
        maxOffset = 0.5f * (scene.getSceneWidth() - projectionWidth);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        limitFrameRate();
        calculateCameraOffset();
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

            for (int spriteIndex = 0; spriteIndex < scene.getSpriteCount(); spriteIndex++) {
                SpriteFactory.Sprite sprite = scene.getSpriteAtIndex(spriteIndex);
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
                Matrix.setIdentityM(scale, 0);
                Matrix.scaleM(scale, 0, sprite.scaleX, sprite.scaleY, 1);
                Matrix.setIdentityM(transform, 0);
                Matrix.setIdentityM(pivot, 0);
                Matrix.translateM(pivot, 0, pivot, 0, sprite.pivotX, sprite.pivotY, 0); // TRANSLATE TO CENTER
                Matrix.setRotateEulerM(rotation, 0, 0, 0, sprite.angle); // EULER ROTATE (?)
                Matrix.multiplyMM(rotation, 0, pivot, 0, rotation, 0);
                Matrix.multiplyMM(scale, 0, pivot, 0, scale, 0);
                Matrix.setIdentityM(pivot, 0);
                Matrix.translateM(pivot, 0, pivot, 0, -sprite.pivotX, -sprite.pivotY, 0); // TRANSLATE TO ORIGINAL
                Matrix.multiplyMM(rotation, 0, rotation, 0, pivot, 0); // CALCULATE ROTATION
                Matrix.multiplyMM(scale, 0, scale, 0, pivot, 0); // CALCULATE SCALE

                //TRANSLATE
                Matrix.setIdentityM(translate, 0);
                Matrix.translateM(translate, 0, sprite.translateX, sprite.translateY, 0);

                //MULTIPLY
                Matrix.multiplyMM(transform, 0, scale, 0, rotation, 0);
                Matrix.multiplyMM(transform, 0, translate, 0, transform, 0); // SCALE * ROTATION * TRANSLATE

                //FINAL MATRIX
                Matrix.multiplyMM(temp, 0, projectionMatrix, 0, transform, 0);

                textureProgram.useProgram();
                textureProgram.setUniforms(temp, scene.getTexture(sprite.getResourceId()), sprite.alpha);
                sprite.bindData(textureProgram);
                sprite.draw();
            }

        theme.specialAnimation();

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

    public static float getWidth() {
        return projectionWidth;
    }

    public static float getHeight() {
        return projectionHeight;
    }


    public void setOffsetDelta(float offsetDelta) {
        if (offsetChanging) return;
        float newOffset = Mathf.clamp(targetXOffset + offsetDelta, -1, 1);
        if (newOffset < targetXOffset || newOffset > targetXOffset) {
            targetXOffset = newOffset;
            startXOffset = currentXOffset;
            lastOffsetChangeStartTime = System.currentTimeMillis();
            offsetChanging = true;
            theme.onOffsetChanged(offsetDelta);
        }
    }

    public int getRunMode() {
        return runMode;
    }

    public float getProjectionWidth() {
        return projectionWidth;
    }

    public float getProjectionHeight() {
        return projectionHeight;
    }

    public Scene getCurrentScene() {
        return scene;
    }

    public void propagateTouchEvent(float screenX, float screenY) {
        if (touchEnabled) {
            Point2D touch = SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, SpaceConverter.screenToWorldPoint(screenX, screenY));
            theme.onSceneTouched(touch.X, touch.Y);
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

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public void setGhostAnimationValue(int ghostAnimationValue) {
        this.ghostAnimationValue = ghostAnimationValue;
    }

    public void setLWPSpeed(int LWPSpeed) {
        this.LWPSpeed = LWPSpeed;
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
            final float normalizedX = (x / screenWidth) * 2 - 1;
            final float normalizedY = -((y / screenHeight) * 2 - 1);
            return new Point2D(normalizedX, normalizedY);
        }
    }

    public class SceneHolder {
        private final Scene scene;
        private final Point2D projectionTopLeft, projectionBotRight;
        private final int screenWidth, screenHeight;

        public SceneHolder(Scene scene, Point2D projectionTopLeft, Point2D projectionBotRight, int screenWidth, int screenHeight) {
            this.scene = scene;
            this.projectionTopLeft = projectionTopLeft;
            this.projectionBotRight = projectionBotRight;
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
        }

        public Scene getScene() {
            return scene;
        }

        public Point2D getProjectionTopLeft() {
            return projectionTopLeft;
        }

        public Point2D getProjectionBotRight() {
            return projectionBotRight;
        }

        public int getScreenWidth() {
            return screenWidth;
        }

        public int getScreenHeight() {
            return screenHeight;
        }
    }

}
