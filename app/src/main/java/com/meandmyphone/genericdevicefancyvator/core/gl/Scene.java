package com.meandmyphone.genericdevicefancyvator.core.gl;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.meandmyphone.genericdevicefancyvator.core.LWPTheme;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.SpritePoint;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.TransitionCallback;
import com.meandmyphone.genericdevicefancyvator.core.util.TextureHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public class Scene {

    private static int counter = 0x0000;
    private final int ID = counter++;
    private SparseIntArray textures = new SparseIntArray();
    private SparseArray<SpriteFactory.Sprite> sprites = new SparseArray<>();
    private List<Integer> deprecatedSprites = new ArrayList<>();
    private Point2D sceneTopLeft, sceneTopCenter, sceneTopRight, sceneCenterRight, sceneBotRight, sceneBotCenter, sceneBotLeft, sceneCenterLeft, sceneCenter;
    private float sceneWidth, sceneHeight;
    private final Projection projection;

    public Scene(Context context, int runMode, Projection projection, LWPTheme theme) {
        this.projection = projection;
        for (int textureResource : theme.getResources()) {
            textures.put(textureResource, TextureHelper.loadTexture(context, textureResource));
        }
        if (runMode == GLRenderer.PORTRAIT_MODE) {
            setSceneHeight(projection.getProjectionHeight());
            setSceneWidth((float) (Math.pow(projection.getProjectionHeight(), 2) / projection.getProjectionWidth()));
            float
                    top = projection.getProjectionPointOfInteres(SpritePoint.TOPCENTER).Y,
                    right = projection.getProjectionPointOfInteres(SpritePoint.CENTER).X + getSceneWidth() / 2,
                    bottom = projection.getProjectionPointOfInteres(SpritePoint.BOTCENTER).Y,
                    left = projection.getProjectionPointOfInteres(SpritePoint.CENTER).X - getSceneWidth() / 2,
                    centerX = projection.getProjectionPointOfInteres(SpritePoint.CENTER).X / 2,
                    centerY = projection.getProjectionPointOfInteres(SpritePoint.CENTER).Y / 2;
            setSceneTopLeft(new Point2D(left, top));
            setSceneTopCenter(new Point2D(centerX, top));
            setSceneTopRight(new Point2D(right, top));
            setSceneCenterRight(new Point2D(right, centerY));
            setSceneBotRight(new Point2D(right, bottom));
            setSceneBotCenter(new Point2D(centerX, bottom));
            setSceneBotLeft(new Point2D(left, bottom));
            setSceneCenterLeft(new Point2D(left, bottom));
            setSceneCenter(new Point2D(centerX, centerY));
        } else if (runMode == GLRenderer.LANDSCAPE_MODE) {
            setSceneWidth(this.projection.getProjectionWidth());
            setSceneHeight(this.projection.getProjectionHeight());
            setSceneTopLeft(this.projection.getProjectionPointOfInteres(SpritePoint.TOPLEFT));
            setSceneTopCenter(this.projection.getProjectionPointOfInteres(SpritePoint.TOPCENTER));
            setSceneTopRight(this.projection.getProjectionPointOfInteres(SpritePoint.TOPRIGHT));
            setSceneCenterRight(this.projection.getProjectionPointOfInteres(SpritePoint.CENTERRIGHT));
            setSceneBotRight(this.projection.getProjectionPointOfInteres(SpritePoint.BOTRIGHT));
            setSceneBotCenter(this.projection.getProjectionPointOfInteres(SpritePoint.BOTCENTER));
            setSceneBotLeft(this.projection.getProjectionPointOfInteres(SpritePoint.BOTLEFT));
            setSceneCenterLeft(this.projection.getProjectionPointOfInteres(SpritePoint.CENTERLEFT));
            setSceneCenter(this.projection.getProjectionPointOfInteres(SpritePoint.CENTER));
        }
    }

    public Point2D getSceneTopLeft() {
        return sceneTopLeft;
    }

    public void setSceneTopLeft(Point2D sceneTopLeft) {
        this.sceneTopLeft = sceneTopLeft;
    }

    public Point2D getSceneTopCenter() {
        return sceneTopCenter;
    }

    public void setSceneTopCenter(Point2D sceneTopCenter) {
        this.sceneTopCenter = sceneTopCenter;
    }

    public Point2D getSceneTopRight() {
        return sceneTopRight;
    }

    public void setSceneTopRight(Point2D sceneTopRight) {
        this.sceneTopRight = sceneTopRight;
    }

    public Point2D getSceneCenterRight() {
        return sceneCenterRight;
    }

    public void setSceneCenterRight(Point2D sceneCenterRight) {
        this.sceneCenterRight = sceneCenterRight;
    }

    public Point2D getSceneBotRight() {
        return sceneBotRight;
    }

    public void setSceneBotRight(Point2D sceneBotRight) {
        this.sceneBotRight = sceneBotRight;
    }

    public Point2D getSceneBotCenter() {
        return sceneBotCenter;
    }

    public void setSceneBotCenter(Point2D sceneBotCenter) {
        this.sceneBotCenter = sceneBotCenter;
    }

    public Point2D getSceneBotLeft() {
        return sceneBotLeft;
    }

    public void setSceneBotLeft(Point2D sceneBotLeft) {
        this.sceneBotLeft = sceneBotLeft;
    }

    public Point2D getSceneCenterLeft() {
        return sceneCenterLeft;
    }

    public void setSceneCenterLeft(Point2D sceneCenterLeft) {
        this.sceneCenterLeft = sceneCenterLeft;
    }

    public Point2D getSceneCenter() {
        return sceneCenter;
    }

    public void setSceneCenter(Point2D sceneCenter) {
        this.sceneCenter = sceneCenter;
    }

    public float getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(float sceneWidth) {
        this.sceneWidth = sceneWidth;
    }

    public float getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(float sceneHeight) {
        this.sceneHeight = sceneHeight;
    }

    public void addSprite(SpriteFactory.Sprite sprite) {
        sprites.put(sprite.ID, sprite);
    }

    public int getID() {
        return ID;
    }

    public int getSpriteCount() {
        return sprites.size();
    }

    public SpriteFactory.Sprite getSprite(int key) {
        return sprites.get(key);
    }

    public SpriteFactory.Sprite getSpriteAtIndex(int index) {
        return sprites.get(sprites.keyAt(index));
    }

    public int getTexture(int resourceId) {
        return textures.get(resourceId);
    }

    public void destroySprite(int spriteID) {
        deprecatedSprites.add(spriteID);
    }

    public void destroySpriteWithEffect(GLRenderer renderer, final int id, DestroyEffect effect) {
        switch (effect) {
            case FADE:
                FadeTransition fadeOut = new FadeTransition(renderer, 500, id, 0.0f);
                fadeOut.setOnTransitionFinished(new TransitionCallback() {
                    @Override
                    public void handleEvent() {
                        destroySprite(id);
                    }
                });
                fadeOut.start();
                break;
        }
    }

    public void onFrameDrawn() {
        Iterator<Integer> it = deprecatedSprites.iterator();
        while (it.hasNext()) {
            int spriteID = it.next();
            if (sprites.get(spriteID) != null) {
                sprites.remove(spriteID);
            }
            it.remove();
        }
    }

    @Override
    public String toString() {
        return "Scene{" +
                "ID=" + ID +
                ", textures=" + textures +
                ", sprites=" + sprites +
                ", sceneTopLeft=" + sceneTopLeft +
                ", sceneBotRight=" + sceneBotRight +
                ", sceneWidth=" + sceneWidth +
                ", sceneHeight=" + sceneHeight +
                '}';
    }

    public enum DestroyEffect {
        FADE
    }
}
