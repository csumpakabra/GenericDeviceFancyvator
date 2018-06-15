package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

public enum CycleType {

    YOYO("yoyo"),

    RESTART("restart");
    private final String value;

    CycleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CycleType fromValue(String v) {
        for (CycleType c: CycleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
