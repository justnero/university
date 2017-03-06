package ru.justnero.study.s6.cg.e2.main.figures;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.TransformOperationExtended;

public class Vertex3f implements TransformOperationExtended<Vertex3f> {
    public float x, y, h;

    protected String name;
    protected Vertex3f result;

    public Vertex3f() {
        this(0, 0, 1f);
    }

    public Vertex3f(float x, float y) {
        this(x, y, 1);
    }

    public Vertex3f(String name, float x, float y) {
        this(name, x, y, 1f);
    }

    protected Vertex3f(float x, float y, float h) {
        this("", x, y, h);
    }

    public Vertex3f(String name, float x, float y, float h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void normalize() {
        x /= h;
        y /= h;
        h = 1f;
    }

    @Override
    public Vertex3f getResultMatrix() {
        return result;
//        return new Vertex3f();
    }

    @Override
    public void transform(Matrix3f translateMatrix, Vertex3f result) {
        // TODO: 17/02/2017 make more effective?
        Vertex3f temp = new Vertex3f();

        translateMatrix.getColumn(0, temp);
        result.x = temp.cross(this);

        translateMatrix.getColumn(1, temp);
        result.y = temp.cross(this);

        translateMatrix.getColumn(2, temp);
        result.h = temp.cross(this);
    }

    public float cross(Vertex3f vertex3F) {
        return x * vertex3F.x + y * vertex3F.y + h * vertex3F.h;
    }

    @Override
    public String toString() {
        return name + "(" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
    }

    @Override
    public void copyTo(Vertex3f vertex3F) {
        vertex3F.x = x;
        vertex3F.y = y;
        vertex3F.h = h;
        vertex3F.name = new String(name);
    }

    @Override
    public void setResult(Vertex3f result) {
        this.result = result;
    }
}
