package com.meandmyphone.genericdevicefancyvator.json.pojo.background;

public enum BackgroundType {


    GRADIENT("gradient"), IMAGE("image");

    private String value;

    BackgroundType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
