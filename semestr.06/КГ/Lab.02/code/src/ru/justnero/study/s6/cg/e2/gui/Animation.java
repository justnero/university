package ru.justnero.study.s6.cg.e2.gui;

import java.util.Arrays;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.TransformOperation;
import ru.justnero.study.s6.cg.e2.util.animation.Animable;

public class Animation implements Runnable {
    protected final Matrix3f transformationMatrix;
    protected final Animable<? extends TransformOperation> transformable;
    protected final Runnable[] update;

    protected float fpsRate = 100f;
    protected float totalTime = 1000f;
    protected float n = 100;

    protected Matrix3f temp = new Matrix3f();

    public Animation(Matrix3f transformationMatrix, Animable<? extends TransformOperation> transformable,
                     Runnable... update) {
        this.transformationMatrix = transformationMatrix;
        this.transformable = transformable;
        this.update = update;

//        transformable.addAnimation(new SimpleAnimation(transformationMatrix, (int) n));
    }

    public Animation() {
        this(null, null, null, null);
    }

    public void setFpsRate(float fpsRate) {
        this.fpsRate = fpsRate;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public void run() {
        transformationMatrix.copyTo(temp);
        n = fpsRate * totalTime / 1000f;

//        for (int i = 0; i < n + 1; i++) {
        while (transformable.hasNextFrame()) {
            transformable.nextFrame();
//            transformableResult.normalize();
            Arrays.stream(update).forEach(Runnable::run);

            try {
                Thread.sleep((long) (1000f / fpsRate));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
