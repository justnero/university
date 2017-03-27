package ru.justnero.study.s6.cg.e2.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import ru.justnero.study.s6.cg.e2.main.figures.AnimableQuad;
import ru.justnero.study.s6.cg.e2.main.figures.Axis;
import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e2.util.Scene;

public class Lab2Scene extends Scene {

    protected final AnimableQuad base = new AnimableQuad();
    protected final AnimableQuad result = new AnimableQuad();
    protected float coordinateSize = 11;
    protected Axis axis = new Axis();
    protected float[] color = {0f, 0.5f, 1f};

    public Lab2Scene() {
        AnimableQuad quad = new AnimableQuad(new Vertex3f[]{
                new Vertex3f("A", 0, 0),
                new Vertex3f("D", 1, 3),
                new Vertex3f("C", 4, 4),
                new Vertex3f("B", 3, 1)
        });

        quad.copyTo(base);
        base.copyTo(result);

        quad.setResult(this.base);
        this.base.setResult(result);

        quad.transform(
                1, 0, 0,
                0, 1, 0,
                1, 1, 1);

        this.base.normalize();

        base.transform(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1);
    }

    public AnimableQuad getBase() {
        return base;
    }

    public AnimableQuad getResult() {
        return result;
    }

    public Axis getAxis() {
        return axis;
    }

    public float getCoordinateSize() {
        return coordinateSize;
    }

    public void setCoordinateSize(float coordinateSize) {
        this.coordinateSize = coordinateSize;
    }

    public void transform(Matrix3f transformationMatrix) {
        base.transform(transformationMatrix);
        result.normalize();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glColor3f(0f, 0f, 1f);
        gl.glLineWidth(0.2f);
        base.display(drawable, coordinateSize, false);

        gl.glColor3fv(color, 0);
        gl.glLineWidth(2f);
        result.display(drawable, coordinateSize, false);
        gl.glLineWidth(1f);

        axis.setCoordinateSize(coordinateSize);
        axis.display(drawable);
    }

    public void nextFrame() {
        base.nextFrame();
        result.nextFrame();
        base.normalize();
        result.normalize();
    }


}
