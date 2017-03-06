package ru.justnero.study.s6.cg.e2.main;

import com.sun.javafx.geom.Vec3f;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;

public class Matrix3f extends com.sun.javafx.geom.Matrix3f {

    public Matrix3f(float m00, float m01, float m02,
                    float m10, float m11, float m12,
                    float m20, float m21, float m22) {

        super(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }

    public Matrix3f(float[] v) {
        super(v);
    }

    public Matrix3f(Vec3f[] v) {
        super(v);
    }

    public Matrix3f(com.sun.javafx.geom.Matrix3f m1) {
        super(m1);
    }

    public Matrix3f() {
    }

    public void setValue(float value, int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        m00 = value;
                        break;
                    case 1:
                        m01 = value;
                        break;
                    case 2:
                        m02 = value;
                        break;
                }
                break;
            case 1:
                switch (j) {
                    case 0:
                        m10 = value;
                        break;
                    case 1:
                        m11 = value;
                        break;
                    case 2:
                        m12 = value;
                        break;
                }
                break;
            case 2:
                switch (j) {
                    case 0:
                        m20 = value;
                        break;
                    case 1:
                        m21 = value;
                        break;
                    case 2:
                        m22 = value;
                        break;
                }
                break;
        }
    }

    public void getColumn(int col, Vertex3f v) {
        if (col == 0) {
            v.x = m00;
            v.y = m10;
            v.h = m20;
        } else if (col == 1) {
            v.x = m01;
            v.y = m11;
            v.h = m21;
        } else if (col == 2) {
            v.x = m02;
            v.y = m12;
            v.h = m22;
        } else {
            throw new ArrayIndexOutOfBoundsException("Matrix3f");
        }
    }

    public void getColumn(int col, float[] v) {
        if (col == 0) {
            v[0] = m00;
            v[1] = m10;
            v[2] = m20;
        } else if (col == 1) {
            v[0] = m01;
            v[1] = m11;
            v[2] = m21;
        } else if (col == 2) {
            v[0] = m02;
            v[1] = m12;
            v[2] = m22;
        } else {
            throw new ArrayIndexOutOfBoundsException("Matrix3f");
        }
    }

    public void copyTo(Matrix3f result) {
        result.m00 = m00;
        result.m01 = m01;
        result.m02 = m02;
        result.m10 = m10;
        result.m11 = m11;
        result.m12 = m12;
        result.m20 = m20;
        result.m21 = m21;
        result.m22 = m22;
    }
}
