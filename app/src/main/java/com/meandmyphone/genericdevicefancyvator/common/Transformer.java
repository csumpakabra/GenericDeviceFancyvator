package com.meandmyphone.genericdevicefancyvator.common;

import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;

import com.meandmyphone.genericdevicefancyvator.json.pojo.Ease;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Transition;

public interface Transformer {
    com.meandmyphone.genericdevicefancyvator.core.transitions.Transition transform(int spriteId, Transition xmlTransition);
    SpriteFactory.Sprite transform(Sprite xmlSprite);
    int transform(Ease xmlEase);
}
