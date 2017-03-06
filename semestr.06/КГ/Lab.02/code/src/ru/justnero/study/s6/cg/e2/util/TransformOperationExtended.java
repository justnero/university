package ru.justnero.study.s6.cg.e2.util;

public interface TransformOperationExtended<T extends TransformOperationExtended>
        extends TransformOperation<T> {

    void setResult(T result);

    void copyTo(T result);

    default void applyChange() {
        getResultMatrix().copyTo(this);
    }

    default void redoChange() {
        this.copyTo(getResultMatrix());
    }
}
