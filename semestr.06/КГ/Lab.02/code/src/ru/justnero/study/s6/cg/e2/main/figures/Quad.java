package ru.justnero.study.s6.cg.e2.main.figures;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.Arrays;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.BaseFigure;

import static ru.justnero.study.s6.cg.e2.main.figures.Axis.fontScale;
import static ru.justnero.study.s6.cg.e2.main.figures.Axis.textRenderer;

public class Quad extends BaseFigure<Quad> {
    protected Vertex3f[] vertices;

    protected boolean labelDisplay = false;
    protected boolean fill = false;

    public Quad() {
        vertices = new Vertex3f[4];

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex3f();
        }
    }

    public Quad(Vertex3f[] vertices) {
        this.vertices = vertices;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public Vertex3f[] getVertices() {
        return vertices;
    }

    public boolean isLabelDisplay() {
        return labelDisplay;
    }

    public void setLabelDisplay(boolean labelDisplay) {
        this.labelDisplay = labelDisplay;
    }

    public void normalize() {
        Arrays.stream(vertices).forEach(Vertex3f::normalize);
    }


    @Override
    public void transform(Matrix3f translateMatrix, Quad result) {
        Vertex3f[] vertices = result.getVertices();

        for (int i = 0; i < result.getVertices().length; i++) {
            this.vertices[i].transform(translateMatrix, vertices[i]);
        }
    }

    public void display(GLAutoDrawable drawable, float coordinateSize) {
        final GL2 gl = drawable.getGL().getGL2();

        displayQuad(gl);
        displayVertices(gl);

        if (labelDisplay) {
            displayLabels(coordinateSize);
        }
    }

    protected void displayQuad(GL2 gl) {
        gl.glBegin(fill ? GL2.GL_QUADS : GL2.GL_LINE_LOOP);
        Arrays.stream(vertices).forEach(vector3f -> gl.glVertex2f(vector3f.x, vector3f.y));
        gl.glEnd();
    }

    private void displayVertices(GL2 gl) {
        gl.glEnable(GL.GL_POINT_SIZE);
        gl.glPointSize(5f);
        gl.glBegin(GL2.GL_POINTS);

        Arrays.stream(vertices).forEach(vector3f -> gl.glVertex2f(vector3f.x, vector3f.y));

        gl.glEnd();
        gl.glDisable(GL.GL_POINT_SIZE);
    }

    private void displayLabels(float coordinateSize) {
        textRenderer.beginRendering((int) (2 * coordinateSize * fontScale), (int) (2 * coordinateSize * fontScale));

        Arrays.stream(vertices).forEach(vector3f -> {
            textRenderer.draw(vector3f.toString(),
                    (int) ((vector3f.x + coordinateSize + 0.3f) * fontScale),
                    (int) ((vector3f.y + coordinateSize + 0.3f) * fontScale));
        });

        textRenderer.endRendering();
    }

    @Override
    public void copyTo(Quad result) {
        for (int i = 0; i < 4; i++) {
            vertices[i].copyTo(result.getVertices()[i]);
        }
    }
}
