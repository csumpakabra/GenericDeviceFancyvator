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

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, float toAlpha) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, 1.0f, toAlpha);
    }

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float toAlpha) {
        this(renderer, cycleDuration, nodeId, easeType, 1.0f, toAlpha);
    }

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, float fromAlpha, float toAlpha) {
        this(renderer, cycleDuration, nodeId, ITransition.DEFAULT, fromAlpha, toAlpha);
    }

    public FadeTransition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float fromAlpha, float toAlpha) {
        super(renderer, cycleDuration, nodeId, easeType);
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        deltaAlpha = toAlpha - fromAlpha;
        Logger.Log(TAG, "Sprite %d will be fading from %f to %f", nodeId, fromAlpha, toAlpha);
    }

    @Override
    public void transit() {
        if (!playing || cyclesDone>=cycleCount) return;
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            sprite.alpha = Ease.calculateFloat(easeType, currentTimeInLoop, fromAlpha, deltaAlpha, cycleDuration);
        } else {
            transitionFinished();
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
