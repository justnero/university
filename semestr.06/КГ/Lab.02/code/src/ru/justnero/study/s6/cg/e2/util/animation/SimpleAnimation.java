package ru.justnero.study.s6.cg.e2.util.animation;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;

public class SimpleAnimation extends AbstractAnimation {
    protected final Matrix3f endTransform;

    public SimpleAnimation(Matrix3f endTransform, int frameNumber) {
        this(endTransform, new Matrix3f(), frameNumber);
    }

    public SimpleAnimation(Matrix3f endTransform, Matrix3f currentResult, int frameNumber) {
        super(currentResult, frameNumber);
        this.endTransform = endTransform;

        endTransform.copyTo(currentResult);
    }

    @Override
    protected void calculateNextFrame() {
        currentResult.m00 = 1 + (((endTransform.m00 - 1) * (currentFrame + 1)) / frameNumber);
        currentResult.m11 = 1 + (((endTransform.m11 - 1) * (currentFrame + 1)) / frameNumber);
        currentResult.m20 = (currentFrame + 1) * (endTransform.m20 / frameNumber);
        currentResult.m21 = (currentFrame + 1) * (endTransform.m21 / frameNumber);
        currentResult.m22 = 1 + (((endTransform.m22 - 1) * (currentFrame + 1)) / frameNumber);

        currentResult.m01 = (currentFrame + 1) * (endTransform.m01 / frameNumber);
        currentResult.m10 = (currentFrame + 1) * (endTransform.m10 / frameNumber);

        currentResult.m02 = (currentFrame + 1) * (endTransform.m02 / frameNumber);
        currentResult.m12 = (currentFrame + 1) * (endTransform.m12 / frameNumber);
    }
}
