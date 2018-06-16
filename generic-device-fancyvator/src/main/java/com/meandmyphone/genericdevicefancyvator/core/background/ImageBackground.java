package com.meandmyphone.genericdevicefancyvator.core.background;

import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;

import static com.meandmyphone.genericdevicefancyvator.core.background.FillType.REPEAT;
import static com.meandmyphone.genericdevicefancyvator.core.background.FillType.STRETCH;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.*;

public class ImageBackground extends Background {
    
    private float [] vertexData;
    private int resourceId;
    private FillType fillType;
    
    public ImageBackground(int resourceId, Scene scene, FillType fillType) {
        if (STRETCH.equals(fillType)) {
            vertexData = new float[]{
                    scene.getScenePointOfInterest(TOPLEFT).X, scene.getScenePointOfInterest(TOPLEFT).Y, 0, 0, // top l
                    scene.getScenePointOfInterest(BOTLEFT).X, scene.getScenePointOfInterest(BOTLEFT).Y, 0, 1, // bot l
                    scene.getScenePointOfInterest(BOTRIGHT).X, scene.getScenePointOfInterest(BOTRIGHT).Y, 1, 1, // bot r
                    scene.getScenePointOfInterest(TOPLEFT).X, scene.getScenePointOfInterest(TOPLEFT).Y, 0, 0, // top l
                    scene.getScenePointOfInterest(TOPRIGHT).X, scene.getScenePointOfInterest(TOPRIGHT).Y, 1, 0, // top r
                    scene.getScenePointOfInterest(BOTRIGHT).X, scene.getScenePointOfInterest(BOTRIGHT).Y, 0, 1}; // bot r
        } else if (REPEAT.equals(fillType)) {
            throw new UnsupportedOperationException("FillType not supported: " + fillType);
        }
        this.resourceId = resourceId;
        vertexArray = new VertexArray(vertexData);
    }
}
