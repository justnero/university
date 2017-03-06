package ru.justnero.study.s6.cg.e3.main;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e2.util.animation.AbstractAnimation;
import ru.justnero.study.s6.cg.e2.util.animation.Animable;

public class AnimableVector3f extends Vertex3f implements Animable<Vertex3f> {
    protected List<AbstractAnimation> animations = new ArrayList<>();

    public AnimableVector3f() {
    }

    public AnimableVector3f(float x, float y) {
        super(x, y);
    }

    public AnimableVector3f(String name, float x, float y) {
        super(name, x, y);
    }

    public AnimableVector3f(float x, float y, float h) {
        super(x, y, h);
    }

    public AnimableVector3f(String name, float x, float y, float h) {
        super(name, x, y, h);
    }

    @Override
    synchronized public List<AbstractAnimation> getAnimations() {
        return animations;
    }
}
