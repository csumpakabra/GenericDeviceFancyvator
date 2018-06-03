package com.meandmyphone.genericdevicefancyvator.core.util;

import android.graphics.Color;

/**
 * Created by csumpakadabra on 2017.10.20..
 */

public class Mathf {

    public static float clamp(float a, float min, float max) {
        if (a < min) return min;
        else if (a > max) return max;
        else return a;
    }

    public static float[] toGLColor(int color) {
        float [] glColor = new float[4];
        glColor[0] = Color.red(color) / 255f;
        glColor[1] = Color.green(color) / 255f;
        glColor[2] = Color.blue(color) / 255f;
        glColor[3] = Color.alpha(color) / 255f;
        return glColor;
    }
}
