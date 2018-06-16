package com.meandmyphone.genericdevicefancyvator.core.background;

import android.content.Context;
import android.opengl.Matrix;

import com.meandmyphone.genericdevicefancyvator.core.programs.ColorShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.programs.ShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.programs.TextureShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.util.Mathf;
import com.meandmyphone.genericdevicefancyvator.core.util.ShaderHelper;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static com.meandmyphone.genericdevicefancyvator.core.gl.Constants.BYTES_PER_FLOAT;

/**
 * Created by csumpakadabra on 2017.10.27..
 */

public class GradientBackground extends Background {


    private ColorShaderProgram colorShaderProgram;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private float[] vertexData;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public GradientBackground(Context context, int topLeftColor, int botLeftColor, int botRightColor, int topRightColor) {

        colorShaderProgram = new ColorShaderProgram(context);

        float [][] colors = new float[4][4];
        colors[0] = Mathf.toGLColor(topLeftColor);
        colors[1] = Mathf.toGLColor(botLeftColor);
        colors[2] = Mathf.toGLColor(botRightColor);
        colors[3] = Mathf.toGLColor(topRightColor);

        vertexData = new float [] {
                -1,1,colors[0][0],colors[0][1],colors[0][2],colors[0][3], // top l
                -1,-1,colors[1][0],colors[1][1],colors[1][2],colors[1][3], // bot l
                1,-1,colors[2][0],colors[2][1],colors[2][2],colors[2][3], // bot r

                -1,1,colors[0][0],colors[0][1],colors[0][2],colors[0][3], // top l
                1,1,colors[3][0],colors[3][1],colors[3][2],colors[3][3], // top r
                1,-1,colors[2][0],colors[2][1],colors[2][2],colors[2][3]  // bot r
        };

        vertexArray = new VertexArray(vertexData);
    }
    @Override
    public void draw() {
        float [] identity = new float[16];
        Matrix.setIdentityM(identity, 0);
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(identity);
        super.bindData();
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    @Override
    protected int getPositionAttributeLocation() {
        return colorShaderProgram.getPositionAttributeLocation();
    }

    @Override
    protected int getFillAtributeLocation() {
        return colorShaderProgram.getColorAttributeLocation();
    }

    @Override
    protected int getPositionComponentCount() {
        return POSITION_COMPONENT_COUNT;
    }

    @Override
    protected int getFillComponentCount() {
        return COLOR_COMPONENT_COUNT;
    }

    @Override
    protected int getStride() {
        return STRIDE;
    }

}