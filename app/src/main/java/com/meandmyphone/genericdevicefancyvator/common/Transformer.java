package com.meandmyphone.genericdevicefancyvator.common;

import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;

import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;

public interface Transformer {
    SpriteFactory.Sprite transform(Sprite xmlSprite);
}
