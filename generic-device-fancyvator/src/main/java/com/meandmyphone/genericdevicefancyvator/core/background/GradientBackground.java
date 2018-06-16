package com.meandmyphone.genericdevicefancyvator.core.background;

import com.meandmyphone.genericdevicefancyvator.core.programs.ColorShaderProgram;
import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;
import com.meandmyphone.genericdevicefancyvator.core.util.Mathf;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static com.meandmyphone.genericdevicefancyvator.core.gl.Constants.BYTES_PER_FLOAT;

/**
 * Created by csumpakadabra on 2017.10.27..
 */

public class GradientBackground extends Background {
    private static int counter = 0xFF00;
    public final int ID = ++counter;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private float[] vertexData;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public GradientBackground(int topLeftColor, int botLeftColor, int botRightColor, int topRightColor) {
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

    public void bindData(ColorShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
    }

}
