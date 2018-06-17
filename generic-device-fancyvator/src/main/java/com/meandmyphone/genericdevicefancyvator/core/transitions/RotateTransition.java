package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.19..
 */

public class RotateTransition extends Transition {
    private String TAG = "RotateTransition " + transitionId;
    private float fromAngle, toAngle, deltaAngle;

    public RotateTransition(GLRenderer renderer, int cycleDuration, int nodeId, int cycleCount, int easeType, int destroyEffect, boolean destroyOnFinished, boolean autoreverse, float fromAngle, float toAngle) {
        super(renderer, cycleDuration, nodeId, cycleCount, easeType, destroyEffect, destroyOnFinished, autoreverse);
        this.fromAngle = fromAngle;
        this.toAngle = toAngle;
        deltaAngle = toAngle - fromAngle;
        Logger.Log(TAG, "Sprite %d will be rotating from %s to %s", nodeId, fromAngle, toAngle);
    }

    @Override
    public void doTransit() {
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.angle = Ease.calculateFloat(easeType, currentTimeInLoop, fromAngle, deltaAngle, cycleDuration);
        } else {
            if (autoreverse) {
                direction *= -1;
                float tempAngle = fromAngle;
                fromAngle = toAngle;
                toAngle = tempAngle;
                deltaAngle = toAngle - fromAngle;
            }
            transitionCycleFinished();
        }
    }
}
