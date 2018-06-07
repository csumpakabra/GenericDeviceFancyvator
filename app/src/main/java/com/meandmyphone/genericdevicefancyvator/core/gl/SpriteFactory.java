package com.meandmyphone.genericdevicefancyvator.core.gl;


import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2DUV;
import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.SpritePoint2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.SpriteUV;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ITransition;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;
import com.meandmyphone.genericdevicefancyvator.core.util.TextureHelper;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static com.meandmyphone.genericdevicefancyvator.core.gl.Constants.BYTES_PER_FLOAT;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public class SpriteFactory {

    private final static String TAG = "SpriteFactory";
    private final Scene scene;

    public SpriteFactory(@NonNull Scene scene) {
        this.scene = scene;
        Logger.Log(TAG, "New spritefactory created:\n%s", toString());
    }

    private Sprite createSprite(int resourceID, float topLeftX, float topLeftY, float sizeX, float sizeY, float topLeftU, float topLeftV, float botRightU, float botRightV) {
        Point2D base = new Point2D(topLeftX, topLeftY);
        Point2D spriteTopLeft = new Point2D(base.X, base.Y);
        Point2D spriteBottomLeft = new Point2D(base.X, base.Y - sizeY);
        Point2D spriteBottomRight = new Point2D(base.X + sizeX, base.Y - sizeY);
        Point2D spriteBottomTopRight = new Point2D(base.X + sizeX, base.Y);

        Point2DUV A = new Point2DUV(spriteTopLeft, topLeftU, topLeftV);
        Point2DUV B = new Point2DUV(spriteBottomLeft, topLeftU, botRightV);
        Point2DUV C = new Point2DUV(spriteBottomRight, botRightU, botRightV);
        Point2DUV D = new Point2DUV(spriteBottomTopRight, botRightU, topLeftV);

        Sprite sprite = new Sprite(Sprite.SPRITE_COUNTER++, resourceID, A, B, C, D);
        Logger.Log(TAG, "New sprite created:\n%s\nPoints:\n%s,%s,%s,%s", sprite.toString(), A, B, C, D);

        scene.addSprite(sprite);
        return sprite;
    }

    public Sprite createSpriteAtSpritePointRelativeWidth(int resourceID, SpritePoint2D spritePoint, float relativeSpriteWidth, SpriteUV spriteUV) {
        float spriteWidthInProjection = scene.getSceneWidth() * relativeSpriteWidth;
        float spriteHeightInProjection = (spriteWidthInProjection * spriteUV.getHeight()) / (TextureHelper.bitmapAspectRatio.get(resourceID) * spriteUV.getWidth());
        Point2D topLeft = calculateTopLeftFromOwnedSpritePoint(spritePoint, spriteWidthInProjection, spriteHeightInProjection);
        return createSprite(
                resourceID,
                topLeft.X,
                topLeft.Y,
                spriteWidthInProjection,
                spriteHeightInProjection,
                spriteUV.getTopLeft().U,
                spriteUV.getTopLeft().V,
                spriteUV.getBotRight().U,
                spriteUV.getBotRight().V
        );
    }

    public Sprite createSpriteAtSpritePointRelativeHeight(int resourceID, SpritePoint2D spritePoint, float relativeSpriteHeight, SpriteUV spriteUV) {
        float spriteHeightInProjection = scene.getSceneHeight() * relativeSpriteHeight;
        float spriteWidthInProjection = (spriteHeightInProjection * spriteUV.getWidth()) / (TextureHelper.bitmapAspectRatio.get(resourceID) * spriteUV.getHeight());
        Point2D topLeft = calculateTopLeftFromOwnedSpritePoint(spritePoint, spriteWidthInProjection, spriteHeightInProjection);
        return createSprite(
                resourceID,
                topLeft.X,
                topLeft.Y,
                spriteWidthInProjection,
                spriteHeightInProjection,
                spriteUV.getTopLeft().U,
                spriteUV.getTopLeft().V,
                spriteUV.getBotRight().U,
                spriteUV.getBotRight().V
        );
    }

    public Sprite createSpriteRelativeToOtherSpriteRelativeWidth(int resourceID, int otherSpriteID, Anchor otherSpriteAnchor, Anchor anchor, float relativeSpriteWidth, SpriteUV spriteUV) {
        Point2D connectionPoint = calculateSpritePointOfSprite(otherSpriteID, otherSpriteAnchor);
        float spriteWidthInProjection = scene.getSceneWidth() * relativeSpriteWidth;
        float spriteHeightInProjection = (spriteWidthInProjection * spriteUV.getHeight()) / (TextureHelper.bitmapAspectRatio.get(resourceID) * spriteUV.getWidth());
        Point2D topLeft = calculateTopLeftFromOwnedSpritePoint(new SpritePoint2D(anchor, connectionPoint.X, connectionPoint.Y), spriteWidthInProjection, spriteHeightInProjection);
        return createSprite(
                resourceID,
                topLeft.X,
                topLeft.Y,
                spriteWidthInProjection,
                spriteHeightInProjection,
                spriteUV.getTopLeft().U,
                spriteUV.getTopLeft().V,
                spriteUV.getBotRight().U,
                spriteUV.getBotRight().V
        );
    }

    public Sprite createSpriteRelativeToOtherSpriteRelativeHeight(int resourceID, int otherSpriteID, Anchor otherSpriteAnchor, Anchor anchor, float relativeSpriteHeight, SpriteUV spriteUV) {
        Point2D connectionPoint = calculateSpritePointOfSprite(otherSpriteID, otherSpriteAnchor);
        float spriteHeightInProjection = scene.getSceneHeight() * relativeSpriteHeight;
        float spriteWidthInProjection = (spriteHeightInProjection * spriteUV.getWidth()) / (TextureHelper.bitmapAspectRatio.get(resourceID) * spriteUV.getHeight());
        Point2D topLeft = calculateTopLeftFromOwnedSpritePoint(new SpritePoint2D(anchor, connectionPoint.X, connectionPoint.Y), spriteWidthInProjection, spriteHeightInProjection);
        return createSprite(
                resourceID,
                topLeft.X,
                topLeft.Y,
                spriteWidthInProjection,
                spriteHeightInProjection,
                spriteUV.getTopLeft().U,
                spriteUV.getTopLeft().V,
                spriteUV.getBotRight().U,
                spriteUV.getBotRight().V
        );
    }

    private Point2D calculateTopLeftFromOwnedSpritePoint(SpritePoint2D point, float spriteWidth, float spriteHeight) {
        float x = point.X;
        float y = point.Y;
        switch (point.getType()) {
            case CENTERLEFT:
                y += spriteHeight / 2;
                break;
            case BOTLEFT:
                y += spriteHeight;
                break;
            case BOTCENTER:
                x -= spriteWidth/2;
                y += spriteHeight;
                break;
            case BOTRIGHT:
                x -= spriteWidth;
                y += spriteHeight;
                break;
            case CENTERRIGHT:
                x -= spriteWidth;
                y += spriteHeight / 2;
                break;
            case TOPRIGHT:
                x -=spriteWidth;
                break;
            case TOPCENTER:
                x -= spriteWidth/2;
                break;
            case CENTER:
                x -= spriteWidth/2;
                y += spriteHeight / 2;
                break;
        }
        return new Point2D(x, y);
    }

    private Point2D calculateSpritePointOfSprite(int spriteId, Anchor anchor) {
        Sprite sprite = scene.getSprite(spriteId);
        float x = sprite.getTopLeftX();
        float y = sprite.getTopLeftY();
        float spriteHeight = sprite.height;
        float spriteWidth = sprite.width;
        switch (anchor) {
            case CENTERLEFT:
                y -= spriteHeight / 2;
                break;
            case BOTLEFT:
                y -= spriteHeight;
                break;
            case BOTCENTER:
                x += spriteWidth/2;
                y -= spriteHeight;
            case BOTRIGHT:
                x += spriteWidth;
                y -= spriteHeight;
                break;
            case CENTERRIGHT:
                x += spriteWidth;
                y -= spriteHeight / 2;
                break;
            case TOPRIGHT:
                x +=spriteWidth;
                break;
            case TOPCENTER:
                x += spriteWidth/2;
                break;
            case CENTER:
                x += spriteWidth/2;
                y -= spriteHeight / 2;
                break;
        }
        return new Point2D(x,y);
    }

    @Override
    public String toString() {
        return "SpriteFactory{" +
                "scene=" + scene +
                '}';
    }

    /**
     * Created by csumpakabra on 2016.09.25..
     */
    public static class Sprite {
        public static int SPRITE_COUNTER = 0xFF00;
        public final int ID;
        public final SparseArray<ITransition> transitions = new SparseArray<>();

        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
        private static final int STRIDE = (POSITION_COMPONENT_COUNT
                + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

        private float[] vertexData;

        private final VertexArray vertexArray;
        private int resourceId;

        private Point2DUV A,B,C,D;
        public float
                alpha = 1.0f,
                angle,
                scaleX = 1.0f,
                scaleY = 1.0f,
                translateX,
                translateY,
                width,
                height,
                pivotX,
                pivotY;

        private Anchor pivot = Anchor.CENTER;

        /**
         *
         * Counter clockwise!
         *
         * @param A top left
         * @param B bottom left
         * @param C bottom right
         * @param D top right
         */
        private Sprite(int ID, int resourceId, Point2DUV A, Point2DUV B, Point2DUV C, Point2DUV D) {
            this.ID = ID;
            vertexData = new float [] {
                    A.X, A.Y, A.U, A.V, // top l
                    B.X, B.Y, B.U, B.V, // bot l
                    C.X, C.Y, C.U, C.V, // bot r
                    A.X, A.Y, A.U, A.V, // top l
                    D.X, D.Y, D.U, D.V, // top r
                    C.X, C.Y, C.U, C.V }; // bot r
            this.resourceId = resourceId;
            this.A = A;
            this.B = B;
            this.C = C;
            this.D = D;
            vertexArray = new VertexArray(vertexData);
            width = A.distance(D);
            height = A.distance(B);
            calculatePivot();
        }



        void bindData(TextureShaderProgram textureProgram) {
            vertexArray.setVertexAttribPointer(
                    0,
                    textureProgram.getPositionAttributeLocation(),
                    POSITION_COMPONENT_COUNT,
                    STRIDE);
            vertexArray.setVertexAttribPointer(
                    POSITION_COMPONENT_COUNT,
                    textureProgram.getTextureCoordinatesAttributeLocation(),
                    TEXTURE_COORDINATES_COMPONENT_COUNT,
                    STRIDE);
        }

        void draw() {
            glDrawArrays(GL_TRIANGLES, 0, 6);
        }


        @Override
        public String toString() {
            return "Sprite{" +
                    "vertexArray=" + vertexArray +
                    '}';
        }

        int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }

        public float getTopLeftX() {
            return A.X;
        }

        public float getTopLeftY() {
            return A.Y;
        }

        public Anchor getPivot() {
            return pivot;
        }

        public float getAlpha() {
            return alpha;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public void setPivot(Anchor pivot) {
            this.pivot = pivot;
            calculatePivot();
        }

        private void calculatePivot() {
            switch (pivot) {
                case TOPLEFT:
                    pivotX = A.X;
                    pivotY = A.Y;
                    break;
                case CENTERLEFT:
                    pivotX = A.X;
                    pivotY = A.Y - height/2;
                    break;
                case BOTLEFT:
                    pivotX = B.X;
                    pivotY = B.Y;
                    break;
                case BOTCENTER:
                    pivotX = B.X + width/2;
                    pivotY = B.Y;
                case BOTRIGHT:
                    pivotX = C.X;
                    pivotY = C.Y;
                    break;
                case CENTERRIGHT:
                    pivotX = C.X;
                    pivotY = C.X + height/2;
                case TOPRIGHT:
                    pivotX = D.X;
                    pivotY = D.Y;
                    break;
                case TOPCENTER:
                    pivotX = D.X - width / 2;
                    pivotY = D.Y;
                    break;
                case CENTER:
                    pivotX = A.X + width/2;
                    pivotY = A.Y - height/2;
                    break;
            }
        }

        public void setTranslateY(float translateY) {
            this.translateY = translateY;
        }

        public void setTranslateX(float translateX) {
            this.translateX = translateX;
        }
    }
}
