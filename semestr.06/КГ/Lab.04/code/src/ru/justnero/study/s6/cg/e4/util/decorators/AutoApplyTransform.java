package ru.justnero.study.s6.cg.e4.util.decorators;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e4.util.Complex;

public class AutoApplyTransform<T extends Complex<T>>
        extends FigureDecorator<T> {

    public AutoApplyTransform(T figure) {
        super(figure);
    }

    @Override
    public void transform(Matrix3f translateMatrix, T result) {
        super.transform(translateMatrix, result);
        applyChange();
    }
}
