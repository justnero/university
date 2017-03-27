package ru.justnero.study.s6.cg.e4.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;
import ru.justnero.study.s6.cg.e4.util.Complex;

public class Quad extends ru.justnero.study.s6.cg.e2.main.figures.Quad implements Complex<ru.justnero.study.s6.cg.e2.main.figures.Quad> {
    protected Color color;

    public Quad() {
        fill = true;
    }

    public Quad(ru.justnero.study.s6.cg.e2.main.figures.Vertex3f[] vertices) {
        super(vertices);
        fill = true;
    }

    @Override
    public void getVertexes(List<Vertex3f> result) {
        for (int i = 0; i < 4; i++) {
            result.add(vertices[i]);
        }
    }

    @Override
    public void display(GLAutoDrawable drawable, float coordinateSize, boolean fill) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glLineWidth(2f);
        if (color != null) {
            gl.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        }

        super.display(drawable, coordinateSize, fill);

        gl.glLineWidth(1f);
    }

    @Override
    protected void displayQuad(GL2 gl, boolean fill) {
        gl.glBegin(fill ? GL2.GL_TRIANGLE_FAN : GL2.GL_LINE_LOOP);
        Arrays.stream(vertices).forEach(vector3f -> gl.glVertex2f(vector3f.x, vector3f.y));
        gl.glEnd();

        if (fill) {
            floodFill(gl);
        }
    }

    protected void floodFill(GL2 gl) {

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
