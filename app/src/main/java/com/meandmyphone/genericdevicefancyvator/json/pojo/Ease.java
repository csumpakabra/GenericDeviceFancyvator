package com.meandmyphone.genericdevicefancyvator.json.pojo;

public enum Ease {

    LINEAR("linear"),

    CUBIC_IN("cubic_in"),

    CUBIC_OUT("cubic_out"),

    CUBIC_INOUT("cubic_inout"),

    QUAD_IN("quad_in"),

    QUAD_OUT("quad_out"),

    QUAD_INOUT("quad_inout"),

    SINE_IN("sine_in"),

    SINE_OUT("sine_out"),

    SINE_INOUT("sine_inout");
    private final String value;

    Ease(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Ease fromValue(String v) {
        for (Ease c: Ease.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}