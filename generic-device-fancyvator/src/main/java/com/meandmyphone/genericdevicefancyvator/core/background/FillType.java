package com.meandmyphone.genericdevicefancyvator.core.background;

public enum FillType {
    REPEAT("repeat"), STRETCH("stretch");
    String value;
    FillType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
