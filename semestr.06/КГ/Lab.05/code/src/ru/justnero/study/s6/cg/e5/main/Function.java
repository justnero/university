package ru.justnero.study.s6.cg.e5.main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Drawable;

public class Function implements Drawable {
    protected List<Vertex3f> points1 = new ArrayList<>();
    protected List<Vertex3f> points2 = new ArrayList<>();
    protected boolean isFirstList = true;
    protected float a, b, c;

    protected float dx;
    protected float xBegin, xEnd, xCurrent;

    public Function(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setA(float a) {
        this.a = a;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setC(float c) {
        this.c = c;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setxBegin(float xBegin) {
        this.xBegin = xBegin;
        reset();
    }

    public void setxEnd(float xEnd) {
        this.xEnd = xEnd;
    }

    public float function(float x) {
        return (float) (a + (b / x) + (c / (x * x)));
    }

    public void reset() {
        xCurrent = xBegin;
        isFirstList = true;
        points1.clear();
        points2.clear();
    }

    public void nextFrame() {
        if (!hasNext()) {
            return;
        }

        float y = function(xCurrent);
        if (Math.abs(y) < 100) {
            if (isFirstList) {
                points1.add(new Vertex3f(xCurrent, y));
            } else {
                points2.add(new Vertex3f(xCurrent, y));
            }
        } else {
            isFirstList = false;
        }

        xCurrent += dx;
    }

    public boolean hasNext() {
        return xCurrent <= xEnd;
    }

    @Override
    public void display(GLAutoDrawable drawable, float coordinateSize, boolean fill) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL.GL_LINE_WIDTH);
        gl.glLineWidth(2f);
        gl.glColor3f(1f, 0f, 0f);

        gl.glBegin(GL2.GL_LINE_STRIP);
        points1.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y));
        gl.glEnd();

        gl.glBegin(GL2.GL_LINE_STRIP);
        points2.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y));
        gl.glEnd();

        gl.glLineWidth(1f);
        gl.glDisable(GL.GL_LINE_WIDTH);
    }
}
