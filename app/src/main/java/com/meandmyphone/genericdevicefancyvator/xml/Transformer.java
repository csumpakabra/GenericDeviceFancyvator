package com.meandmyphone.genericdevicefancyvator.xml;

import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;

interface Transformer {
    com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.FadeTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.TranslateTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.RotateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.RotateTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.ScaleTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.ScaleTransition xmlTransition);
    com.meandmyphone.genericdevicefancyvator.core.transitions.FlipBookAnimation transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.FlipbookTransition xmlTransition);
    SpriteFactory.Sprite transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite xmlSprite);
    SpriteFactory.Sprite transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.Background xmlImageBackground);
    com.meandmyphone.genericdevicefancyvator.core.primitives.FullScreenRectangle transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.DiffuseBackground xmlDiffuseBackground);
    com.meandmyphone.genericdevicefancyvator.core.LWPTheme
    int transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Ease xmlEase);

}
