package com.meandmyphone.genericdevicefancyvator.core.background;

import com.meandmyphone.genericdevicefancyvator.core.data.VertexArray;

public abstract class Background {

    protected VertexArray vertexArray;

    protected final void bindData() {
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

    public abstract void draw();

}