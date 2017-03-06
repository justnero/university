package ru.justnero.study.s6.cg.e2.util.animation;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;

public abstract class AbstractAnimation {
    protected final Matrix3f currentResult;
    protected int frameNumber;

    protected int currentFrame = 0;

    public AbstractAnimation(int frameNumber) {
        this(new Matrix3f(), frameNumber);
    }

    public AbstractAnimation(Matrix3f currentResult, int frameNumber) {
        this.currentResult = currentResult;
        this.frameNumber = frameNumber;
    }


    public final Matrix3f nextFrame() {
        if (currentFrame >= frameNumber) {
            return currentResult;
        }

        calculateNextFrame();

        currentFrame++;
        return currentResult;
    }

    protected abstract void calculateNextFrame();

    public final boolean hasNextFrame() {
        return currentFrame < frameNumber;
    }
}
