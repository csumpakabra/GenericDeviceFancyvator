package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.ITransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Transition;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.19..
 */

public class ScaleTransition extends Transition {

    private String TAG = "ScaleTransition " + transitionId;
    private float fromX, fromY, toX, toY, deltaX, deltaY;

    public ScaleTransition(GLRenderer renderer, int cycleDuration, int nodeId, float fromX, float toX, float fromY, float toY) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, fromX, toX, fromY, toY);
    }

    public ScaleTransition(GLRenderer renderer, int cycleDuration, int nodeId, float toX, float toY) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, 1, toX, 1, toY);
    }

    public ScaleTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float toX, float toY) {
        this(renderer, cycleDuration, nodeId, easeType, 1, toX, 1, toY);
    }

    public ScaleTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float fromX, float toX, float fromY, float toY) {
        super(renderer, cycleDuration, nodeId, easeType);
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        deltaX = toX - fromX;
        deltaY = toY - fromY;
        Logger.Log(TAG,"Sprite %d will be scaling from (%f, %f) to (%f, %f)", nodeId, fromX, fromY, toX, toY);
    }

    @Override
    public void transit() {
        if (!playing || cyclesDone>=cycleCount) return;
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.scaleX = Ease.calculateFloat(easeType, currentTimeInLoop, fromX, deltaX, cycleDuration);
            sprite.scaleY = Ease.calculateFloat(easeType, currentTimeInLoop, fromY, deltaY, cycleDuration);

        } else {
            transitionFinished();
            if (autoreverse) {
                direction *= -1;
                float tempX = fromX;
                float tempY = fromY;
                fromX = toX;
                fromY = toY;
                toX = tempX;
                toY = tempY;
                deltaX = toX - fromX;
                deltaY = toY - fromY;
            }
            transit();
        }
    }
}
