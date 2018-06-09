package com.meandmyphone.genericdevicefancyvator.json.pojo;

public enum Pivot {


    TOP_LEFT("top_left"),

    TOP_CENTER("top_center"),

    TOP_RIGHT("top_right"),

    CENTER_RIGHT("center_right"),

    BOT_RIGHT("bot_right"),

    BOT_CENTER("bot_center"),

    BOT_LEFT("bot_left"),

    CENTER_LEFT("center_left"),

    CENTER("center");
    private final String value;

    Pivot(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Pivot fromValue(String v) {
        for (Pivot c: Pivot.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
