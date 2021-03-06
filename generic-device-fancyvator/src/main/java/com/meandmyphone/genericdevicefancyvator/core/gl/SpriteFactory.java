package com.meandmyphone.genericdevicefancyvator.core.gl;


import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2DUV;
import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ITransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.Transition;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

import java.util.HashMap;
import java.util.Map;

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

    public Sprite createSprite(int ID, int resourceID, float topLeftX, float topLeftY, float sizeX, float sizeY, float topLeftU, float topLeftV, float botRightU, float botRightV, float alpha, float scaleX, float scaleY, float rotation, Anchor pivot, int sortOrder) {
        Point2D base = new Point2D(topLeftX, topLeftY);
        Point2D spriteTopLeft = new Point2D(base.X, base.Y);
        Point2D spriteBottomLeft = new Point2D(base.X, base.Y - sizeY);
        Point2D spriteBottomRight = new Point2D(base.X + sizeX, base.Y - sizeY);
        Point2D spriteBottomTopRight = new Point2D(base.X + sizeX, base.Y);

        Point2DUV A = new Point2DUV(spriteTopLeft, topLeftU, topLeftV);
        Point2DUV B = new Point2DUV(spriteBottomLeft, topLeftU, botRightV);
        Point2DUV C = new Point2DUV(spriteBottomRight, botRightU, botRightV);
        Point2DUV D = new Point2DUV(spriteBottomTopRight, botRightU, topLeftV);

        Sprite sprite = new Sprite(ID, resourceID, A, B, C, D, sortOrder);
        Logger.Log(TAG, "New sprite created:\n%s\nPoints:\n%s,%s,%s,%s", sprite.toString(), A, B, C, D);

        sprite.setAlpha(alpha);
        sprite.setAngle(rotation);
        sprite.setPivot(pivot);
        sprite.setScaleX(scaleX);
        sprite.setScaleY(scaleY);
        scene.addSprite(sprite);
        return sprite;
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
        private int sortOrder;

        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
        private static final int STRIDE = (POSITION_COMPONENT_COUNT
                + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

        private float[] vertexData;

        private VertexArray vertexArray;
        private int resourceId;
        private Map<Anchor, Point2D> pointsOfInterest;

        private Point2DUV A, B, C, D;
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
         * Counter clockwise!
         *
         * @param A top left
         * @param B bottom left
         * @param C bottom right
         * @param D top right
         */
        private Sprite(int ID, int resourceId, Point2DUV A, Point2DUV B, Point2DUV C, Point2DUV D, int sortOrder) {
            this.ID = ID;
            vertexData = new float[]{
                    A.X, A.Y, A.U, A.V, // top l
                    B.X, B.Y, B.U, B.V, // bot l
                    C.X, C.Y, C.U, C.V, // bot r
                    A.X, A.Y, A.U, A.V, // top l
                    D.X, D.Y, D.U, D.V, // top r
                    C.X, C.Y, C.U, C.V}; // bot r
            this.resourceId = resourceId;
            this.A = A;
            this.B = B;
            this.C = C;
            this.D = D;
            this.sortOrder = sortOrder;
            vertexArray = new VertexArray(vertexData);
            width = A.distance(D);
            height = A.distance(B);
            calculatePivot();
            createPointsOfInterest();
        }

        public void addTransition(Transition transition) {
            this.transitions.put(transition.getTransitionId(), transition);
        }

        public void refreshUV(int resourceId, float top_left_u, float top_left_v, float bot_right_u, float bot_right_v) {
            vertexData = new float[]{
                    A.X, A.Y, top_left_u, top_left_v, // top l
                    B.X, B.Y, top_left_u, bot_right_v, // bot l
                    C.X, C.Y, bot_right_u, bot_right_v, // bot r
                    A.X, A.Y, top_left_u, top_left_v, // top l
                    D.X, D.Y, bot_right_u, top_left_v, // top r
                    C.X, C.Y, bot_right_u, bot_right_v}; // bot r
            this.resourceId = resourceId;
            vertexArray = new VertexArray(vertexData);
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
                    pivotY = A.Y - height / 2;
                    break;
                case BOTLEFT:
                    pivotX = B.X;
                    pivotY = B.Y;
                    break;
                case BOTCENTER:
                    pivotX = B.X + width / 2;
                    pivotY = B.Y;
                case BOTRIGHT:
                    pivotX = C.X;
                    pivotY = C.Y;
                    break;
                case CENTERRIGHT:
                    pivotX = C.X;
                    pivotY = C.X + height / 2;
                case TOPRIGHT:
                    pivotX = D.X;
                    pivotY = D.Y;
                    break;
                case TOPCENTER:
                    pivotX = D.X - width / 2;
                    pivotY = D.Y;
                    break;
                case CENTER:
                    pivotX = A.X + width / 2;
                    pivotY = A.Y - height / 2;
                    break;
            }
        }

        public void setTranslateY(float translateY) {
            this.translateY = translateY;
        }

        public void setTranslateX(float translateX) {
            this.translateX = translateX;
        }

        public void setScaleX(float scaleX) {
            this.scaleX = scaleX;
        }

        public void setScaleY(float scaleY) {
            this.scaleY = scaleY;
        }

        private void createPointsOfInterest() {
            pointsOfInterest = new HashMap<>();
            pointsOfInterest.put(Anchor.TOPLEFT, new Point2D(A.X, A.Y));
            pointsOfInterest.put(Anchor.TOPCENTER, new Point2D(A.X + width / 2, A.Y));
            pointsOfInterest.put(Anchor.TOPRIGHT, new Point2D(D.X, D.Y));
            pointsOfInterest.put(Anchor.CENTERRIGHT, new Point2D(D.X, D.Y - height / 2));
            pointsOfInterest.put(Anchor.BOTRIGHT, new Point2D(C.X, C.Y));
            pointsOfInterest.put(Anchor.BOTCENTER, new Point2D(B.X + width / 2, C.Y));
            pointsOfInterest.put(Anchor.BOTLEFT, new Point2D(B.X, B.Y));
            pointsOfInterest.put(Anchor.CENTERLEFT, new Point2D(B.X, A.Y - height / 2));
            pointsOfInterest.put(Anchor.CENTER, new Point2D(A.X + width / 2, A.Y - height / 2));
        }

        public Point2D getPointOfInterest(Anchor anchor) {
            return pointsOfInterest.get(anchor);
        }

        public int getSortOrder() {
            return sortOrder;
        }
    }
}
