package ru.justnero.study.s6.cg.e3.util;

import ru.justnero.study.s6.cg.e2.util.TransformOperation;

public interface Rotation<T extends Rotation<T>> extends TransformOperation<T> {

    default T rotate(float radian) {
        float sin = (float) Math.sin(radian), cos = (float) Math.cos(radian);

        return transform(
                cos, sin, 0,
                -sin, cos, 0,
                0, 0, 1);
    }

    default T rotateByDegrees(float degrees) {
        return rotate((float) Math.toRadians(degrees));
    }
}
