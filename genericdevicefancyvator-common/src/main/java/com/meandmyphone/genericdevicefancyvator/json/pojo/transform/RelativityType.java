
package com.meandmyphone.genericdevicefancyvator.json.pojo.transform;

public enum RelativityType {


    SCENE("scene"),

    SPRITE("sprite"),

    NONE("none");
    private final String value;

    RelativityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelativityType fromValue(String v) {
        for (RelativityType c: RelativityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
