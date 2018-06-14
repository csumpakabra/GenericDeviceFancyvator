
package com.meandmyphone.genericdevicefancyvator.json.pojo;

public class Position {

    private PositionType positionType;
    private Pivot relativePointOfTarget;
    private Measure xDistanceFromTarget;
    private Measure yDistanceFromTarget;

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public Pivot getRelativePointOfTarget() {
        return relativePointOfTarget;
    }

    public void setRelativePointOfTarget(Pivot relativePointOfTarget) {
        this.relativePointOfTarget = relativePointOfTarget;
    }

    public Measure getXDistanceFromTarget() {
        return xDistanceFromTarget;
    }

    public void setXDistanceFromTarget(Measure xDistanceFromTarget) {
        this.xDistanceFromTarget = xDistanceFromTarget;
    }

    public Measure getYDistanceFromTarget() {
        return yDistanceFromTarget;
    }

    public void setYDistanceFromTarget(Measure yDistanceFromTarget) {
        this.yDistanceFromTarget = yDistanceFromTarget;
    }
}
