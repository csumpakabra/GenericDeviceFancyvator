package com.meandmyphone.genericdevicefancyvator.transitions;

import com.meandmyphone.genericdevicefancyvator.core.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.ITransition;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.Transition;
import com.meandmyphone.genericdevicefancyvator.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.19..
 */

public class RotateTransition extends Transition {
    private String TAG = "RotateTransition " + transitionId;
    private float fromAngle, toAngle, deltaAngle;

    public RotateTransition(GLRenderer renderer, int cycleDuration, int nodeId, float toAngle) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, 0, toAngle);
    }

    public RotateTransition(GLRenderer renderer, int cycleDuration, int nodeId, float fromAngle, float toAngle) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, fromAngle, toAngle);
    }

    public RotateTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float toAngle) {
        this(renderer, cycleDuration, nodeId, easeType, 0, toAngle);
    }

    public RotateTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float fromAngle, float toAngle) {
        super(renderer, cycleDuration, nodeId, easeType);
        this.fromAngle = fromAngle;
        this.toAngle = toAngle;
        deltaAngle = toAngle - fromAngle;
        Logger.Log(TAG, "Sprite %d will be rotating from %s to %s", nodeId, fromAngle, toAngle);
    }

    @Override
    public void transit() {
        if (!playing || cyclesDone>=cycleCount) return;
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.angle = Ease.calculateFloat(easeType, currentTimeInLoop, fromAngle, deltaAngle, cycleDuration);

        } else {
            transitionFinished();
            if (autoreverse) {
                direction *= -1;
                float tempAngle = fromAngle;
                fromAngle = toAngle;
                toAngle = tempAngle;
                deltaAngle = toAngle - fromAngle;
            }
            transit();
        }
    }
}
