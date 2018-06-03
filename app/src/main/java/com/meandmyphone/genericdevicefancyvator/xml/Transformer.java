package com.meandmyphone.genericdevicefancyvator.xml;

import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;

interface Transformer {
    com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.FadeTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.TranslateTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.RotateTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.RotateTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.ScaleTransition transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.ScaleTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.FlipBookAnimation transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.FlipbookTransition xmlTransition);
    SpriteFactory.Sprite transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite xmlSprite);
    SpriteFactory.Sprite transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Background xmlImageBackground);
    com.meandmyphone.genericdevicefancyvator.core.primitives.FullScreenRectangle transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.DiffuseBackground xmlDiffuseBackground);
    int transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Ease xmlEase);

}
