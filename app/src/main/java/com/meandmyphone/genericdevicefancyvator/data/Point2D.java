package com.meandmyphone.genericdevicefancyvator.data;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public class Point2D {
    public float X,Y;

    public Point2D(float x, float y) {
        X = x;
        Y = y;
    }

    public float distance(Point2D other) {
        return (float) Math.sqrt(Math.pow(X - other.X,2) + Math.pow(Y - other.Y, 2));
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
