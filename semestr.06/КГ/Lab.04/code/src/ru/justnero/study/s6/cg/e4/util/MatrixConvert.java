package ru.justnero.study.s6.cg.e4.util;

import com.sun.javafx.geom.Matrix3f;

public class MatrixConvert extends Matrix3f {
    protected final Vector2D vector2D;

    public MatrixConvert(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public static void convert(Vector2D vector2D, Matrix3f matrix3f) {
        matrix3f.setIdentity();
        matrix3f.m20 = vector2D.x;
        matrix3f.m21 = vector2D.y;
    }

    public static void secondConvert(float sceneSpeed, Matrix3f matrix3f) {
        matrix3f.m20 /= sceneSpeed;
        matrix3f.m21 /= sceneSpeed;
    }

    public Vector2D getVector2D() {
        return vector2D;
    }

    public void convert() {
        setIdentity();
        m20 = vector2D.x;
        m21 = vector2D.y;
    }
}
