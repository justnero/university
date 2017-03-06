package ru.justnero.study.s6.cg.e3.util;

import ru.justnero.study.s6.cg.e2.util.TransformOperation;

public interface RotationAround<T extends RotationAround<T>> extends TransformOperation<T> {

    default T rotateAround(float radian, float x, float y) {
        float sin = (float) Math.sin(radian), cos = (float) Math.cos(radian);

        return transform(
                cos, sin, 0,
                -sin, cos, 0,
                x * (1 - cos) + y * sin, y * (1 - cos) - x * sin, 1);
    }

    default T rotateAroundByDegrees(float degrees, float x, float y) {
        return rotateAround((float) Math.toRadians(degrees), x, y);
    }
}
