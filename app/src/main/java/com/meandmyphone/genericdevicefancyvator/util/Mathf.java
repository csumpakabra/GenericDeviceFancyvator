package com.meandmyphone.genericdevicefancyvator.util;

/**
 * Created by csumpakadabra on 2017.10.20..
 */

public class Mathf {

    public static float clamp(float a, float min, float max) {
        if (a < min) return min;
        else if (a > max) return max;
        else return a;
    }
}
