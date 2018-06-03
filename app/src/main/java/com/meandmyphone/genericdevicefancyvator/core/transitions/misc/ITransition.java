package com.meandmyphone.genericdevicefancyvator.core.transitions.misc;

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

    void transit();
    void start();
}
