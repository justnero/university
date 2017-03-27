package ru.justnero.study.s6.cg.e4.util;

import java.awt.*;

import ru.justnero.study.s6.cg.e2.util.TransformOperationExtended;


public interface Complex<T extends TransformOperationExtended> extends Drawable, TransformOperationExtended<T>, VertexGetter {
    Color getColor();
}
