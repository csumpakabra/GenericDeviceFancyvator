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

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, int destroyEffect, boolean destroyWhenFinished, boolean autoreverse, float fromAlpha, float toAlpha) {
        super(renderer, cycleDuration, nodeId, easeType, destroyEffect, destroyWhenFinished, autoreverse);
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        deltaAlpha = toAlpha - fromAlpha;
        Logger.Log(TAG, "Sprite %d will be fading from %f to %f", nodeId, fromAlpha, toAlpha);
    }

    @Override
    public void transit() {
        super.transit();
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.alpha = Ease.calculateFloat(easeType, currentTimeInLoop, fromAlpha, deltaAlpha, cycleDuration);
        } else {
            transitionCycleFinished();
            if (autoreverse) {
                direction *= -1;
                float tempAlpha = fromAlpha;
                fromAlpha = toAlpha;
                toAlpha = tempAlpha;
                deltaAlpha = toAlpha - fromAlpha;
            }
            transit();
        }
    }
}
