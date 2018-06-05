package com.meandmyphone.genericdevicefancyvator.core.gl;

import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.SpritePoint;

import java.util.HashMap;
import java.util.Map;

public class Projection {

    private float[] projectionMatrix;
    private float projectionWidth, projectionHeight;
    private Map<SpritePoint, Point2D> pointsOfInterest;

    public Projection(float [] projectionMatrix, int width, int height) {
        this.projectionMatrix = projectionMatrix;
        pointsOfInterest = new HashMap<>();

        Point2D projectionTopLeft = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(0, 0));
        Point2D projectionTopCenter = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width / 2, 0));
        Point2D projectionTopRight = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width, 0));
        Point2D projectionCenterRight = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width, height / 2));
        Point2D projectionBotRight = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width, height));
        Point2D projectionBotCenter = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width / 2, height));
        Point2D projectionBotLeft = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(0, height));
        Point2D projectionCenterLeft = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(0, height / 2));
        Point2D projectionCenter = GLRenderer.SpaceConverter.worldToProjectionSpacePoint(projectionMatrix, GLRenderer.SpaceConverter.screenToWorldPoint(width / 2, height / 2));

        pointsOfInterest.put(SpritePoint.TOPLEFT, projectionTopLeft);
        pointsOfInterest.put(SpritePoint.TOPCENTER, projectionTopCenter);
        pointsOfInterest.put(SpritePoint.TOPRIGHT, projectionTopRight);
        pointsOfInterest.put(SpritePoint.CENTERRIGHT, projectionCenterRight);
        pointsOfInterest.put(SpritePoint.BOTRIGHT, projectionBotRight);
        pointsOfInterest.put(SpritePoint.BOTCENTER, projectionBotCenter);
        pointsOfInterest.put(SpritePoint.BOTLEFT, projectionBotLeft);
        pointsOfInterest.put(SpritePoint.CENTERLEFT, projectionCenterLeft);
        pointsOfInterest.put(SpritePoint.CENTER, projectionCenter);

        projectionWidth = projectionBotRight.X - projectionTopLeft.X;
        projectionHeight = projectionTopLeft.Y - projectionBotRight.Y;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    public float getProjectionWidth() {
        return projectionWidth;
    }

    public float getProjectionHeight() {
        return projectionHeight;
    }

    public Point2D getProjectionPointOfInteres(SpritePoint spritePoint) {
        return pointsOfInterest.get(spritePoint);
    }
}
