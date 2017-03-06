package ru.justnero.study.s6.cg.e2.main.figures;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.util.animation.AbstractAnimation;
import ru.justnero.study.s6.cg.e2.util.animation.Animable;

public class AnimableQuad extends Quad implements Animable<Quad> {
    protected List<AbstractAnimation> animations = new ArrayList<>();

    public AnimableQuad() {
    }

    public AnimableQuad(Vertex3f[] vertices) {
        super(vertices);
    }

    @Override
    synchronized public List<AbstractAnimation> getAnimations() {
        return animations;
    }
}
