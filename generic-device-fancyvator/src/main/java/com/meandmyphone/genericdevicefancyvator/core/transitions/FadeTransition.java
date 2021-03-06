package com.meandmyphone.genericdevicefancyvator.core.transitions;


import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.19..
 */

public class FadeTransition extends Transition {

    private final String TAG = "FadeTransition " + transitionId;
    float fromAlpha, toAlpha, deltaAlpha;

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, int cycleCount, int easeType, int destroyEffect, boolean autoreverse, float fromAlpha, float toAlpha) {
        super(renderer, cycleDuration, nodeId, cycleCount, easeType, destroyEffect, autoreverse);
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        deltaAlpha = toAlpha - fromAlpha;
        Logger.Log(TAG, "Sprite %d will be fading from %f to %f", nodeId, fromAlpha, toAlpha);
    }

    @Override
    public void doTransit() {
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= getCycleDuration()) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.alpha = Ease.calculateFloat(easeType, currentTimeInLoop, fromAlpha, deltaAlpha, getCycleDuration());
        } else {
            transitionCycleFinished();
        }
    }

    @Override
    protected void changeDirectionAndReset() {
        direction *= -1;
        float tempAlpha = fromAlpha;
        fromAlpha = toAlpha;
        toAlpha = tempAlpha;
        deltaAlpha = toAlpha - fromAlpha;
    }
}