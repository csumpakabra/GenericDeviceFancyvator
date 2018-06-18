package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

import java.util.ArrayList;
import java.util.List;

public class SequentialTransition extends BaseTransition {
    private List<BaseTransition> transitions;

    public List<BaseTransition> getTransitions() {
        if (transitions == null) {
            transitions = new ArrayList<>();
        }
        return this.transitions;
    }
}
