package com.meandmyphone.genericdevicefancyvator.core.data.misc;

import com.meandmyphone.genericdevicefancyvator.xml.pojo.Pivot;

/**
 * Created by csumpakadabra on 2017.10.21..
 */
public enum Anchor {
    TOPLEFT, CENTERLEFT, BOTLEFT, BOTCENTER, BOTRIGHT, CENTERRIGHT, TOPRIGHT, TOPCENTER, CENTER
    ;

    public static Anchor fromPivot(com.meandmyphone.genericdevicefancyvator.xml.pojo.Pivot pivot) {
        switch (pivot) {
            case TOP_LEFT: return TOPLEFT;
            case TOP_CENTER: return TOPCENTER;
            case TOP_RIGHT: return TOPRIGHT;
            case CENTER_RIGHT: return CENTERRIGHT;
            case BOT_RIGHT: return BOTRIGHT;
            case BOT_CENTER: return BOTCENTER;
            case BOT_LEFT: return BOTLEFT;
            case CENTER_LEFT: return CENTERLEFT;
            case CENTER: return CENTER;
        }
        throw new IllegalArgumentException("Invalid Pivot: " + pivot);
    }
}
