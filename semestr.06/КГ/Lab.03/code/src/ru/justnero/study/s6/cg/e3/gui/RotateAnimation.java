package ru.justnero.study.s6.cg.e3.gui;

import java.util.Arrays;

import ru.justnero.study.s6.cg.e2.gui.Animation;
import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.TransformOperationExtended;
import ru.justnero.study.s6.cg.e2.util.animation.Animable;

public class RotateAnimation extends Animation {

    public RotateAnimation(Matrix3f transformationMatrix, Animable<? extends TransformOperationExtended> transformable,
                           Runnable... update) {
        super(transformationMatrix, transformable, update);
    }

    public RotateAnimation() {
    }

    @Override
    public void run() {
        transformationMatrix.copyTo(temp);
        n = fpsRate * totalTime / 1000f;

        while (transformable.hasNextFrame()) {

            transformable.nextFrame();
            ((TransformOperationExtended) transformable).applyChange();

            Arrays.stream(update).forEach(Runnable::run);

            try {
                Thread.sleep((long) (1000f / fpsRate));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
