package ru.justnero.study.s6.cg.e2.util;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;

public interface TransformOperation<T extends TransformOperation> {

    T getResultMatrix();

    default Matrix3f getTranslateMatrix(float a, float b, float p,
                                        float d, float e, float q,
                                        float l, float m, float s) {
        return new Matrix3f(
                a, b, p,
                d, e, q,
                l, m, s);
    }

    default T transform(Matrix3f translateMatrix) {
        T resultMatrix = getResultMatrix();
        transform(translateMatrix, resultMatrix);
        resultMatrix.normalize();
        return resultMatrix;
    }

    void normalize();

    void transform(Matrix3f translateMatrix, T result);


    default T transform(float a, float b, float p,
                        float d, float e, float q,
                        float l, float m, float s) {

        Matrix3f translateMatrix = getTranslateMatrix(
                a, b, p,
                d, e, q,
                l, m, s);

        return transform(translateMatrix);
    }

    public interface Translate<T extends Translate<T>> extends TransformOperation<T> {
        default T translate(float x, float y) {
            return transform(
                    1, 0, 0,
                    0, 1, 0,
                    x, y, 1
            );
        }

        default T translateX(float x) {
            return translate(x, 0);
        }

        default T translateY(float y) {
            return translate(0, y);
        }
    }

    public interface Shift extends TransformOperation {
        default void shift(float x, float y) {
            transform(
                    1, y, 0,
                    x, 1, 0,
                    0, 0, 1
            );
        }

        default void shiftX(float x) {
            shift(x, 0);
        }

        default void shiftY(float y) {
            shift(0, y);
        }
    }

    public interface Scale<T extends Translate<T>> extends TransformOperation<T> {
        default void scale(float x, float y, float xy) {
            transform(
                    x, 0, 0,
                    0, y, 0,
                    0, 0, xy
            );
        }

        default void scaleX(float x) {
            scale(x, 1, 1);
        }

        default void scaleY(float y) {
            scale(1, y, 1);
        }

        default void scale(float xy) {
            scale(1, 1, xy);
        }
    }

    public static interface Mirror extends TransformOperation {
        default void mirror(boolean x, boolean y, boolean origin) {
            transform(
                    x ? -1 : +1, 0, 0,
                    0, y ? -1 : +1, 0,
                    0, 0, origin ? -1 : +1
            );
        }

        default void mirrorX(boolean x) {
            mirror(x, false, false);
        }

        default void mirrorY(boolean y) {
            mirror(false, y, false);
        }

        default void mirrorOrigin(boolean origin) {
            mirror(false, false, origin);
        }
    }

    public static interface PerspectiveProjection extends TransformOperation {
        default void perspectiveProjection(float p, float q) {
            transform(
                    1, 0, p,
                    0, 1, q,
                    0, 0, 1
            );
        }
    }
}
