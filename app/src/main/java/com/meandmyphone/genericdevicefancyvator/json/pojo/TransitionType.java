
package com.meandmyphone.genericdevicefancyvator.json.pojo;

public enum TransitionType {


    FADE("fade"),

    ROTATE("rotate"),

    SCALE("scale"),

    TRANSLATE("translate"),

    FLIPBOOK("flipbook");
    private final String value;

    TransitionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransitionType fromValue(String v) {
        for (TransitionType c: TransitionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
