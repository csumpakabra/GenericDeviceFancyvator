package com.meandmyphone.genericdevicefancyvator.data.misc;

import com.meandmyphone.genericdevicefancyvator.data.Point2D;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public class SpritePoint2D extends Point2D {
    private final SpritePoint type;
    public SpritePoint2D(SpritePoint type, float x, float y) {
        super(x, y);
        this.type = type;
    }

    public SpritePoint getType() {
        return type;
    }
}
