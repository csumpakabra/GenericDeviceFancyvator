package com.meandmyphone.genericdevicefancyvator.core.primitives;

import android.graphics.Color;

import com.meandmyphone.genericdevicefancyvator.core.programs.ColorShaderProgram;
import com.meandmyphone.genericdevicefancyvator.data.VertexArray;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static com.meandmyphone.genericdevicefancyvator.core.Constants.BYTES_PER_FLOAT;

/**
 * Created by csumpakadabra on 2017.10.27..
 */

public class FullScreenRectangle {
    private static int counter = 0xFF00;
    public final int ID = ++counter;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private float[] vertexData;
    private final VertexArray vertexArray;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public FullScreenRectangle(int topLeftColor, int botLeftColor, int botRightColor, int topRightColor) {
        float [][] colors = new float[4][4];
        colors[0] = toGLColor(topLeftColor);
        colors[1] = toGLColor(botLeftColor);
        colors[2] = toGLColor(botRightColor);
        colors[3] = toGLColor(topRightColor);

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

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    private float[] toGLColor(int color) {
        float [] glColor = new float[4];
        glColor[0] = Color.red(color) / 255f;
        glColor[1] = Color.green(color) / 255f;
        glColor[2] = Color.blue(color) / 255f;
        glColor[3] = Color.alpha(color) / 255f;
        return glColor;
    }
}
