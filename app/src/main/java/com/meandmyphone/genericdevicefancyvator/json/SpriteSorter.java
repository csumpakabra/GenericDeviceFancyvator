package com.meandmyphone.genericdevicefancyvator.json;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.meandmyphone.genericdevicefancyvator.json.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Transition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.TranslateTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import static com.meandmyphone.genericdevicefancyvator.json.pojo.PositionType.SCENE_RELATIVE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.PositionType.SPRITE_RELATIVE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.RelativityType.*;

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

//        while (it.hasNext() || it.hasPrevious()) {
            while (it.hasNext()) {
            Sprite sprite = it.next();
//            if (direction == 1) {
//                if (it.hasNext()) {
//                    sprite = it.next();
//                } else {
//                    if (it.hasPrevious()) {
//                        direction = -1;
//                    } else {
//                        direction = 0;
//                        continue;
//                    }
//                }
//            } else if (direction == -1) {
//                if (it.hasPrevious()) {
//                    sprite = it.previous();
//                } else {
//                    if (it.hasNext()) {
//                        direction = 1;
//                    } else {
//                        direction = 0;
//                        continue;
//                    }
//                }
//            } else {
//                break;
//            }
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
        sortedSprites.add( sprite);
    }

    private List<Sprite> extractDependencies(final Sprite sprite) {
        return new ArrayList<Sprite>(){
            {
                Set<String> marked = new HashSet<>();
                 if (SPRITE_RELATIVE.equals(sprite.getSpriteTransform().getPosition().getPositionType())) {
                    SpriteRelativePosition spriteRelativePosition = (SpriteRelativePosition)sprite.getSpriteTransform().getPosition();
                    marked.add(spriteRelativePosition.getRelativeSpriteId());
                } else if (SCENE_RELATIVE.equals(sprite.getSpriteTransform().getPosition().getPositionType())) {
                    SceneRelativePosition sceneRelativePosition = (SceneRelativePosition)sprite.getSpriteTransform().getPosition();
                    if (SPRITE.equals(sceneRelativePosition.getXDistanceFromTarget().relativity)) {
                        marked.add(sceneRelativePosition.getXDistanceFromTarget().getRelativeTo());
                    }
                    if (SPRITE.equals(sceneRelativePosition.getYDistanceFromTarget().relativity)) {
                        marked.add(sceneRelativePosition.getYDistanceFromTarget().getRelativeTo());
                    }
                }
                if (SPRITE.equals(sprite.getSpriteTransform().getWidth().getRelativity())) {
                    marked.add(sprite.getSpriteTransform().getWidth().getRelativeTo());
                }
                if (SPRITE.equals(sprite.getSpriteTransform().getHeight().getRelativity())) {
                    marked.add(sprite.getSpriteTransform().getHeight().getRelativeTo());
                }
                for (Transition transition : sprite.getTransition()) {
                    if (transition instanceof TranslateTransition) {
                        TranslateTransition translateTransition = (TranslateTransition) transition;
                        if (SPRITE.equals(translateTransition.getByX().getRelativity())) {
                            marked.add(translateTransition.getByX().getRelativeTo());
                        }
                        if (SPRITE.equals(translateTransition.getByY().getRelativity())) {
                            marked.add(translateTransition.getByY().getRelativeTo());
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
        if (!spritesById.containsKey(id)) {
            throw new IllegalArgumentException("Invalid spriteId: " + id);
        }
        return spritesById.get(id);
    }
}
