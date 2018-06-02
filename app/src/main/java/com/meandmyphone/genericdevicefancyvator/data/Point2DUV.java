package com.meandmyphone.genericdevicefancyvator.data;

/**
 * Created by csumpakadabra on 2017.10.18..
 */
public class Point2DUV extends Point2D{
    public float U, V;

    public Point2DUV(float x, float y, float u, float v) {
        super(x,y);
        U = u;
        V = v;
    }

    public Point2DUV(Point2D position, float u, float v) {
        this(position.X, position.Y, u, v);
    }

    @Override
    public String toString() {
        return ("Point2DUV{" +
                "X=" + X +
                ", Y=" + Y +
                ", U=" + U +
                ", V=" + V +
                "}\n");
    }
}
