package com.meandmyphone.genericdevicefancyvator.core.background;

import android.content.Context;
import android.opengl.Matrix;

import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.gl.Projection;
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
    
    public ImageBackground(Context context, int resourceId, FillType fillType, float topLeftU, float topLeftV, float botRightU, float botRightV) {
        textureShaderProgram = new TextureShaderProgram(context);
        texture = TextureHelper.loadTexture(context, resourceId);

        if (STRETCH.equals(fillType)) {
            vertexData = new float[]{
                    -1, 1, topLeftU, topLeftV, // top l
                    -1, -1, topLeftU, botRightV, // bot l
                    1, -1, botRightU, botRightV, // bot r
                    -1, 1, topLeftU, topLeftV, // top l
                    1, 1, botRightV, topLeftV, // top r
                    1, -1, botRightU, botRightV}; // bot r
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
