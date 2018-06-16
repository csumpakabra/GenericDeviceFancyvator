package com.meandmyphone.genericdevicefancyvator.core.background;

import android.content.Context;
import android.opengl.Matrix;

import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;
import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.util.TextureHelper;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static com.meandmyphone.genericdevicefancyvator.core.background.FillType.REPEAT;
import static com.meandmyphone.genericdevicefancyvator.core.background.FillType.STRETCH;
import static com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor.*;
import static com.meandmyphone.genericdevicefancyvator.core.gl.Constants.BYTES_PER_FLOAT;

public class ImageBackground extends Background {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private float [] vertexData;
    private final TextureShaderProgram textureShaderProgram;
    private final int texture;
    
    public ImageBackground(Context context, int resourceId, Scene scene, FillType fillType) {
        textureShaderProgram = new TextureShaderProgram(context);
        texture = TextureHelper.loadTexture(context, resourceId);

        if (STRETCH.equals(fillType)) {
            vertexData = new float[]{
                    scene.getScenePointOfInterest(TOPLEFT).X, scene.getScenePointOfInterest(TOPLEFT).Y, 0, 0, // top l
                    scene.getScenePointOfInterest(BOTLEFT).X, scene.getScenePointOfInterest(BOTLEFT).Y, 0, 1, // bot l
                    scene.getScenePointOfInterest(BOTRIGHT).X, scene.getScenePointOfInterest(BOTRIGHT).Y, 1, 1, // bot r
                    scene.getScenePointOfInterest(TOPLEFT).X, scene.getScenePointOfInterest(TOPLEFT).Y, 0, 0, // top l
                    scene.getScenePointOfInterest(TOPRIGHT).X, scene.getScenePointOfInterest(TOPRIGHT).Y, 1, 0, // top r
                    scene.getScenePointOfInterest(BOTRIGHT).X, scene.getScenePointOfInterest(BOTRIGHT).Y, 1, 1}; // bot r
        } else if (REPEAT.equals(fillType)) {
            throw new UnsupportedOperationException("FillType not supported: " + fillType);
        }
        vertexArray = new VertexArray(vertexData);
    }

    @Override
    protected int getPositionAttributeLocation() {
        return textureShaderProgram.getPositionAttributeLocation();
    }

    @Override
    protected int getFillAtributeLocation() {
        return textureShaderProgram.getTextureCoordinatesAttributeLocation();
    }

    @Override
    protected int getPositionComponentCount() {
        return POSITION_COMPONENT_COUNT;
    }

    @Override
    protected int getFillComponentCount() {
        return TEXTURE_COORDINATES_COMPONENT_COUNT;
    }

    @Override
    protected int getStride() {
        return STRIDE;
    }

    @Override
    public void draw() {
        float [] identity = new float[16];
        Matrix.setIdentityM(identity, 0);
        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(identity, texture, 1.0f);
        super.bindData();
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
