package ru.justnero.study.s6.cg.e2.util.animation;

import java.util.List;

import ru.justnero.study.s6.cg.e2.util.TransformOperation;

public interface Animable<T extends TransformOperation> extends TransformOperation<T> {

    List<AbstractAnimation> getAnimations();

    default void addAnimation(AbstractAnimation simpleAnimation) {
        getAnimations().add(simpleAnimation);
    }

    default void removeAllAnimation() {
        getAnimations().removeAll(getAnimations());
    }

    default void nextFrame() {
        for (int i = 0; i < getAnimations().size(); i++) {
            if (getAnimations().get(i).hasNextFrame()) {
                transform(getAnimations().get(i).nextFrame());
            } else {
                getAnimations().remove(i);
            }
        }
    }

    default boolean hasNextFrame() {
        return getAnimations().size() > 0;
    }

}
