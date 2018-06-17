package com.meandmyphone.genericdevicefancyvator.core.transitions;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public interface ITransition {

    int
            LINEAR = 0x100F,
            QUADRATICEASEIN = 0x1010,
            QUADRATICEASEOUT = 0x1011,
            QUADRATICEASEINOUT = 0x1012,
            CUBICEASEIN = 0x1013,
            CUBICEASEOUT = 0x1014,
            CUBICEASEINOUT = 0x1015,
            SINEASEIN = 0x1016,
            SINEASEOUT = 0x1017,
            SINEASEINOUT = 0x1018,
            DEFAULT = LINEAR;

    int NUMBEROFTRANSITIONS = 10;

    int
            DESTORY_EFFECT_NONE = 0x2000,
            DESTROY_EFFECT_FADE = 0x2001;

    int CYCLE_INDEFINITE = 0xFFFF;

    void transit();
    void start();
}
