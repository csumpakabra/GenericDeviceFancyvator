package com.meandmyphone.genericdevicefancyvator.core.background;

import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;

public abstract class Background {

    protected VertexArray vertexArray;

    public void bindData() {
        vertexArray.setVertexAttribPointer(
                0,
                getPositionAttributeLocation(),
                getPositionComponentCount(),
                getStride());
        vertexArray.setVertexAttribPointer(
                getPositionComponentCount(),
                getFillAtributeLocation(),
                getFillComponentCount(),
                getStride());
    }

    protected abstract int getPositionAttributeLocation();

    protected abstract int getFillAtributeLocation();

    protected abstract int getPositionComponentCount();

    protected abstract int getFillComponentCount();

    protected abstract int getStride();

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

}