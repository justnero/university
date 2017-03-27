package ru.justnero.study.s6.cg.e5.main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Drawable;

public class AngleFunction implements Drawable {
    protected float a;
    protected float dT;
    protected float beginT = 0, endT, currentT;
    protected List<Vertex3f> points = new ArrayList<>();

    public AngleFunction(float a) {
        this.a = a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public void setBeginT(float beginT) {
        this.beginT = beginT / a;

        reset();
    }

    public void setEndT(float endT) {
        this.endT = endT / a;
    }

    public void setDt(float dT) {
        this.dT = dT;
    }

    public float functionX(float t) {
        return (float) (a * (t - Math.sin(t)));
    }

    public float functionY(float t) {
        return (float) (a * (1 - Math.cos(t)));
    }

    public void nextFrame() {
        if (!hasNext()) {
            return;
        }

        points.add(new Vertex3f(
                functionX(currentT),
                functionY(currentT)
        ));

        currentT += dT;
    }

    public boolean hasNext() {
        return currentT < endT;
    }

    public void reset() {
        currentT = beginT;
        points.clear();
    }

    @Override
    public void display(GLAutoDrawable drawable, float coordinateSize, boolean fill) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL.GL_LINE_WIDTH);
        gl.glLineWidth(2f);
        gl.glColor3f(1f, 0f, 0f);

        gl.glBegin(GL2.GL_LINE_STRIP);
        points.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y));
        gl.glEnd();

        gl.glLineWidth(1f);
        gl.glDisable(GL.GL_LINE_WIDTH);
    }
}
