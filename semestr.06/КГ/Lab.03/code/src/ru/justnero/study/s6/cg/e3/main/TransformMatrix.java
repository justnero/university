package ru.justnero.study.s6.cg.e3.main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import ru.justnero.study.s6.cg.e3.util.Rotation;
import ru.justnero.study.s6.cg.e3.util.RotationAround;
import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.TransformOperation;

public class TransformMatrix extends Matrix3f implements
        Rotation<TransformMatrix>,
        RotationAround<TransformMatrix>,
        TransformOperation.Translate<TransformMatrix> {

    @Override
    public TransformMatrix transform(float a, float b, float p,
                                     float d, float e, float q,
                                     float l, float m, float s) {
        m00 = a;
        m01 = b;
        m02 = p;

        m10 = d;
        m11 = e;
        m12 = q;

        m20 = l;
        m21 = m;
        m22 = s;

        return this;
    }

    @Override
    public TransformMatrix getResultMatrix() {
        return this;
    }

    @Override
    public void transform(Matrix3f translateMatrix, TransformMatrix result) {

    }

    @Override
    public void normalize() {
        throw new NotImplementedException();
    }
}
