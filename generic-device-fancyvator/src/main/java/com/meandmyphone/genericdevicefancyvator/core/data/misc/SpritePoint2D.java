package com.meandmyphone.genericdevicefancyvator.core.data.misc;

import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public class SpritePoint2D extends Point2D {
    private final Anchor type;
    public SpritePoint2D(Anchor type, float x, float y) {
        super(x, y);
        this.type = type;
    }

    public Anchor getType() {
        return type;
    }
}
