package com.meandmyphone.genericdevicefancyvator.xml;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.primitives.FullScreenRectangle;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FlipBookAnimation;
import com.meandmyphone.genericdevicefancyvator.core.transitions.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.ITransition;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Background;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.DiffuseBackground;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Ease;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite;

public class GDFTransformer implements Transformer {

    private GLRenderer renderer;

    public GDFTransformer(GLRenderer renderer) {
        this.renderer = renderer;
    }

    //GLRenderer renderer, int cycleDuration, int nodeId, int easeType, float fromAlpha, float toAlpha
    @Override
    public FadeTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.FadeTransition xmlTransition) {
        return new FadeTransition(renderer, xmlTransition.getDuration().intValue(), 0, transform(xmlTransition.getEase()), (float) xmlTransition.getFromAlpha(), (float) xmlTransition.getToAlpha());
    }

    @Override
    public TranslateTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.TranslateTransition xmlTransition) {
        return null;
    }

    @Override
    public RotateTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.RotateTransition xmlTransition) {
        return null;
    }

    @Override
    public ScaleTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.ScaleTransition xmlTransition) {
        return null;
    }

    @Override
    public FlipBookAnimation transform(FlipbookTransition xmlTransition) {
        return null;
    }

    @Override
    public SpriteFactory.Sprite transform(Sprite xmlSprite) {
        return null;
    }

    @Override
    public SpriteFactory.Sprite transform(Background xmlImageBackground) {
        return null;
    }

    @Override
    public FullScreenRectangle transform(DiffuseBackground xmlDiffuseBackground) {
        return null;
    }

    @Override
    public int transform(Ease xmlEase) {
        switch (xmlEase) {
            case LINEAR: return ITransition.LINEAR;
            case CUBIC_IN: return ITransition.CUBICEASEIN;
            case CUBIC_OUT: return ITransition.CUBICEASEOUT;
            case CUBIC_INOUT: return ITransition.CUBICEASEINOUT;
            case QUAD_IN: return ITransition.QUADRATICEASEIN;
            case QUAD_OUT: return ITransition.QUADRATICEASEINOUT;
            case QUAD_INOUT: return ITransition.QUADRATICEASEINOUT;
            case SINE_IN: return ITransition.SINEASEIN;
            case SINE_OUT: return ITransition.SINEASEOUT;
            case SINE_INOUT: return ITransition.SINEASEINOUT;
        }
        throw new IllegalArgumentException("Unable to transform xmlEase: " + xmlEase);
    }
}
