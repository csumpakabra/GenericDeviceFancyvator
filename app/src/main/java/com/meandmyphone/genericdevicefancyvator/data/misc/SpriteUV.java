package com.meandmyphone.genericdevicefancyvator.data.misc;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public class SpriteUV {
    private final UVVector topLeft, botRight;

    public SpriteUV(UVVector topLeft, UVVector botRight) {
        this.topLeft = topLeft;
        this.botRight = botRight;
    }

    public UVVector getTopLeft() {
        return topLeft;
    }

    public UVVector getBotRight() {
        return botRight;
    }

    public float getWidth() {
        return botRight.U - topLeft.U;
    }

    public float getHeight() {
        return botRight.V - topLeft.V;
    }
}
