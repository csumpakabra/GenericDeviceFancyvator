package com.meandmyphone.genericdevicefancyvator.json.pojo.background;

import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Ease;

public class ImageBackground extends Background {


    private String resource;
    private float topLeftU, topLeftV, botRightU, botRightV;
    private FillType fillType;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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

    public FillType getFillType() {
        return fillType;
    }

    public void setFillType(FillType fillType) {
        this.fillType = fillType;
    }
}
