package com.meandmyphone.genericdevicefancyvator.transformer;

import com.meandmyphone.genericdevicefancyvator.core.background.Background;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.Sprite;

public interface Transformer {
    Sprite transform(com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite xmlSprite);
    Background transform(com.meandmyphone.genericdevicefancyvator.json.pojo.background.Background background);
}
