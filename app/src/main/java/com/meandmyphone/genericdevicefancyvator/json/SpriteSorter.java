package com.meandmyphone.genericdevicefancyvator.json;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.meandmyphone.genericdevicefancyvator.json.pojo.RelativityType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class SpriteSorter {

    private final List<Sprite> originalSprites;
    private final Map<String, Sprite> spritesById;

    public SpriteSorter(List<Sprite> originalSprites) {
        this.originalSprites = originalSprites;
        spritesById = new HashMap<>();
        for (Sprite s : originalSprites) {
            spritesById.put(s.getId(), s);
        }
    }

    public List<Sprite> sortByDependents() {
        List<Sprite> remainingSprites = new ArrayList<>(originalSprites);
        LinkedList<Sprite> sortedSprites = new LinkedList<>();
        List<Sprite> permanentSprites = new ArrayList<>();
        ListIterator<Sprite> it = remainingSprites.listIterator();

        int direction = 1;

        while (it.hasNext() || it.hasPrevious()) {
            Sprite sprite = null;
            if (direction == 1) {
                if (it.hasNext()) {
                    sprite = it.next();
                } else {
                    if (it.hasPrevious()) {
                        direction = -1;
                    } else {
                        direction = 0;
                        continue;
                    }
                }
            } else if (direction == -1) {
                if (it.hasPrevious()) {
                    sprite = it.previous();
                } else {
                    if (it.hasNext()) {
                        direction = 1;
                    } else {
                        direction = 0;
                        continue;
                    }
                }
            } else {
                break;
            }

            visit(sprite, null, permanentSprites, sortedSprites);

        }
        return sortedSprites;
    }

    private void visit(Sprite sprite, @Nullable List<Sprite> temporary, @NonNull List<Sprite> permanent, @NonNull LinkedList<Sprite> sortedSprites) {
        if (permanent.contains(sprite)) {
            return;
        }
        List<Sprite> temp = null == temporary ? new ArrayList<Sprite>() : temporary;
        if (temp.contains(sprite)) throw new IllegalArgumentException("Circular dependency");
        temp.add(sprite);
        for (Sprite s : extractDependencies(sprite)) {
            visit(s, temp, permanent, sortedSprites);
        }
        permanent.add(sprite);
        sortedSprites.add(0, sprite);
    }

    private List<Sprite> extractDependencies(final Sprite sprite) {
        return new ArrayList<Sprite>(){
            {
                Set<String> marked = new HashSet<>();
                if (sprite.getSpriteTransform().getPosition()) {
                    marked.add(sprite.getSpriteTransform().getPosition()
                                    .getSpriteRelativePosition().getRelativeSpriteId());
                } else if (sprite.getSpriteTransform().getPosition().getSceneRelativePosition() != null) {
                    marked.add(sprite.getSpriteTransform().getPosition()
                            .getSceneRelativePosition().getXDistanceFromPivot().getRelativeTo());
                    marked.add(sprite.getSpriteTransform().getPosition()
                            .getSceneRelativePosition().getYDistanceFromPivot().getRelativeTo());
                }
                if (sprite.getSpriteTransform().getWidth().getRelativity() == RelativityType.SPRITE) {
                    marked.add(sprite.getSpriteTransform().getWidth().getRelativeTo());
                }
                if (RelativityType.SPRITE.equals(sprite.getSpriteTransform().getHeight().getRelativity())) {
                    marked.add(sprite.getSpriteTransform().getHeight().getRelativeTo());
                }
                for (Sprite.Transition transition : sprite.getTransition()) {
                    if (transition.getTranslateTransition() != null) {
                        if (RelativityType.SPRITE.equals(transition.getTranslateTransition().getByX().getRelativity())) {
                            marked.add(transition.getTranslateTransition().getByX().getRelativeTo());
                        }

                        if (RelativityType.SPRITE.equals(transition.getTranslateTransition().getByY().getRelativity())) {
                            marked.add(transition.getTranslateTransition().getByY().getRelativeTo());
                        }
                    }
                }
                for (String id : marked) {
                    add(getSpriteFor(id));
                }
            }
        };
    }

    private Sprite getSpriteFor(String id) {
        return spritesById.get(id);
    }
}
