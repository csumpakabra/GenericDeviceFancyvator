package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

public class Frame {
    private float topLeftU, topLeftV, botRightU, botRightV;
    private String resource;

    public Frame() {
    }

    public float getTopLeftU() {
        return topLeftU;
    }

    public void setTopLeftU(float topLeftU) {
        this.topLeftU = topLeftU;
    }

    public float getTopLeftV() {
        return topLeftV;
    }

    public void setTopLeftV(float topLeftV) {
        this.topLeftV = topLeftV;
    }

    public float getBotRightU() {
        return botRightU;
    }

    public void setBotRightU(float botRightU) {
        this.botRightU = botRightU;
    }

    public float getBotRightV() {
        return botRightV;
    }

    public void setBotRightV(float botRightV) {
        this.botRightV = botRightV;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
