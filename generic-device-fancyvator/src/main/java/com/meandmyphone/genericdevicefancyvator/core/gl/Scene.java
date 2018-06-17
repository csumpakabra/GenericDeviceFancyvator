package com.meandmyphone.genericdevicefancyvator.core.gl;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.meandmyphone.genericdevicefancyvator.core.background.Background;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.Sprite;
import com.meandmyphone.genericdevicefancyvator.core.util.TextureHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.BOTCENTER;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.BOTLEFT;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.BOTRIGHT;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.CENTER;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.CENTERLEFT;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.CENTERRIGHT;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.TOPCENTER;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.TOPLEFT;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.TOPRIGHT;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public class Scene {

    private static int counter = 0x0000;
    private final int ID = counter++;
    private SparseIntArray textures = new SparseIntArray();
    private SparseArray<Sprite> sprites = new SparseArray<>();
    private List<Integer> deprecatedSprites = new ArrayList<>();
    private Point2D
            sceneTopLeft,
            sceneTopCenter,
            sceneTopRight,
            sceneCenterRight,
            sceneBotRight,
            sceneBotCenter,
            sceneBotLeft,
            sceneCenterLeft,
            sceneCenter;
    private float sceneWidth, sceneHeight;
    private final Projection projection;
    private final Map<Anchor, Point2D> pointsOfInterest;
    private Background background;

    public Scene(Context context, int runMode, Projection projection, Integer[] resources) {
        this.pointsOfInterest = new HashMap<>();
        this.projection = projection;

        for (int textureResource : resources) {
            textures.put(textureResource, TextureHelper.loadTexture(context, textureResource));
        }
        if (runMode == GLRenderer.PORTRAIT_MODE) {
            setSceneHeight(projection.getProjectionHeight());
            setSceneWidth((float) (Math.pow(projection.getProjectionHeight(), 2) / projection.getProjectionWidth()));
            float
                    top = projection.getProjectionPointOfInterest(Anchor.TOPCENTER).Y,
                    right = projection.getProjectionPointOfInterest(Anchor.CENTER).X + getSceneWidth() / 2,
                    bottom = projection.getProjectionPointOfInterest(Anchor.BOTCENTER).Y,
                    left = projection.getProjectionPointOfInterest(Anchor.CENTER).X - getSceneWidth() / 2,
                    centerX = projection.getProjectionPointOfInterest(Anchor.CENTER).X,
                    centerY = projection.getProjectionPointOfInterest(Anchor.CENTER).Y;
            setSceneTopLeft(new Point2D(left, top));
            setSceneTopCenter(new Point2D(centerX, top));
            setSceneTopRight(new Point2D(right, top));
            setSceneCenterRight(new Point2D(right, centerY));
            setSceneBotRight(new Point2D(right, bottom));
            setSceneBotCenter(new Point2D(centerX, bottom));
            setSceneBotLeft(new Point2D(left, bottom));
            setSceneCenterLeft(new Point2D(left, centerY));
            setSceneCenter(new Point2D(centerX, centerY));
        } else if (runMode == GLRenderer.LANDSCAPE_MODE) {
            setSceneWidth(this.projection.getProjectionWidth());
            setSceneHeight(this.projection.getProjectionHeight());
            setSceneTopLeft(this.projection.getProjectionPointOfInterest(TOPLEFT));
            setSceneTopCenter(this.projection.getProjectionPointOfInterest(Anchor.TOPCENTER));
            setSceneTopRight(this.projection.getProjectionPointOfInterest(Anchor.TOPRIGHT));
            setSceneCenterRight(this.projection.getProjectionPointOfInterest(Anchor.CENTERRIGHT));
            setSceneBotRight(this.projection.getProjectionPointOfInterest(Anchor.BOTRIGHT));
            setSceneBotCenter(this.projection.getProjectionPointOfInterest(Anchor.BOTCENTER));
            setSceneBotLeft(this.projection.getProjectionPointOfInterest(Anchor.BOTLEFT));
            setSceneCenterLeft(this.projection.getProjectionPointOfInterest(Anchor.CENTERLEFT));
            setSceneCenter(this.projection.getProjectionPointOfInterest(Anchor.CENTER));
        }
    }

    private void setSceneTopLeft(Point2D sceneTopLeft) {
        pointsOfInterest.put(TOPLEFT, sceneTopLeft);
        this.sceneTopLeft = sceneTopLeft;
    }

    private void setSceneTopCenter(Point2D sceneTopCenter) {
        pointsOfInterest.put(TOPCENTER, sceneTopCenter);
        this.sceneTopCenter = sceneTopCenter;
    }

    private void setSceneTopRight(Point2D sceneTopRight) {
        pointsOfInterest.put(TOPRIGHT, sceneTopRight);
        this.sceneTopRight = sceneTopRight;
    }

    private void setSceneCenterRight(Point2D sceneCenterRight) {
        pointsOfInterest.put(CENTERRIGHT, sceneCenterRight);
        this.sceneCenterRight = sceneCenterRight;
    }

    private void setSceneBotRight(Point2D sceneBotRight) {
        pointsOfInterest.put(BOTRIGHT, sceneBotRight);
        this.sceneBotRight = sceneBotRight;
    }

    private void setSceneBotCenter(Point2D sceneBotCenter) {
        pointsOfInterest.put(BOTCENTER, sceneBotCenter);
        this.sceneBotCenter = sceneBotCenter;
    }

    private void setSceneBotLeft(Point2D sceneBotLeft) {
        pointsOfInterest.put(BOTLEFT, sceneBotLeft);
        this.sceneBotLeft = sceneBotLeft;
    }

    private void setSceneCenterLeft(Point2D sceneCenterLeft) {
        pointsOfInterest.put(CENTERLEFT, sceneCenterLeft);
        this.sceneCenterLeft = sceneCenterLeft;
    }

    private void setSceneCenter(Point2D sceneCenter) {
        pointsOfInterest.put(CENTER, sceneCenter);
        this.sceneCenter = sceneCenter;
    }

    public Point2D getScenePointOfInterest(Anchor anchor) {
        return pointsOfInterest.get(anchor);
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

    public void addSprite(Sprite sprite) {
        sprites.put(sprite.ID, sprite);
    }

    public int getID() {
        return ID;
    }

    public int getSpriteCount() {
        return sprites.size();
    }

    public Sprite getSprite(int key) {
        return sprites.get(key);
    }

    public Sprite getSpriteAtIndex(int index) {
        return sprites.get(sprites.keyAt(index));
    }

    public int getTexture(int resourceId) {
        return textures.get(resourceId);
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void destroySprite(int spriteID) {
        deprecatedSprites.add(spriteID);
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
}
