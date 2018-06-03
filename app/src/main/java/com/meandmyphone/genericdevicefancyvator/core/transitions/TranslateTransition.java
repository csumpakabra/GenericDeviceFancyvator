package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.Ease;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public class TranslateTransition extends Transition {

    private String TAG = "TranslateTransition "+transitionId;
    public float fromX, fromY, toX, toY, deltaX, deltaY, previousX, previousY;
    private final Point2D A,B;

    public TranslateTransition(GLRenderer renderer, Point2D B, int cycleDuration, int nodeId) {
        this(renderer,new Point2D(0,0), B, cycleDuration, nodeId, ITransition.DEFAULT);
    }

    public TranslateTransition(GLRenderer renderer, Point2D A, Point2D B, int cycleDuration, int nodeId) {
        this(renderer,A, B, cycleDuration, nodeId, ITransition.DEFAULT);
    }

    public TranslateTransition(GLRenderer renderer, Point2D B, int cycleDuration, int nodeId, int easeType) {
        this(renderer,new Point2D(0,0), B, cycleDuration, nodeId, easeType);
    }

    public TranslateTransition(GLRenderer renderer, Point2D A, Point2D B, int cycleDuration, int nodeId, int easeType) {
        super(renderer,cycleDuration, nodeId, easeType);
        this.A = A;
        this.B = B;
        fromX = A.X;
        fromY = A.Y;
        toX = B.X;
        toY = B.Y;
        deltaX = B.X - A.X;
        deltaY = B.Y - A.Y;
        Logger.Log(TAG, "Sprite %d will be moving from %s to %s", nodeId, A.toString(), B.toString());
    }

    @Override
    public void transit() {
        if (!playing) return;
        long currentTimeInLoop = System.currentTimeMillis() - cycleStartTime;
        if (currentTimeInLoop <= cycleDuration) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);
            float x = Ease.calculateFloat(easeType, currentTimeInLoop, fromX, deltaX, cycleDuration);
            float y = Ease.calculateFloat(easeType, currentTimeInLoop, fromY, deltaY, cycleDuration);
            sprite.translateX += x - previousX ;
            sprite.translateY += y - previousY;
            previousX = x;
            previousY = y;
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
