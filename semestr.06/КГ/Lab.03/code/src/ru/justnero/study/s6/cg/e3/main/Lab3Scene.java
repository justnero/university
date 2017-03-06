package ru.justnero.study.s6.cg.e3.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import ru.justnero.study.s6.cg.e2.main.Lab2Scene;
import ru.justnero.study.s6.cg.e2.main.figures.Quad;
import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;

import static ru.justnero.study.s6.cg.e2.main.figures.Axis.fontScale;
import static ru.justnero.study.s6.cg.e2.main.figures.Axis.textRenderer;

public class Lab3Scene extends Lab2Scene {
    protected final AnimableVector3f vertexE = new AnimableVector3f("E", 0, 0);
    protected final Vertex3f resultVertexE = new Vertex3f();
    protected final Quad secondResult = new Quad();

    public Lab3Scene() {
        super();

        color = new float[]{0.862745098f, 0.078431373f, 0.235294118f};
        result.copyTo(secondResult);
        result.setResult(secondResult);

        vertexE.copyTo(resultVertexE);
        vertexE.setResult(resultVertexE);
    }

    public AnimableVector3f getVertexE() {
        return vertexE;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);
        final GL2 gl = drawable.getGL().getGL2();

        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        gl.glVertex2f(vertexE.x, vertexE.y);
        gl.glColor3f(0.1f, 0.1f, 0.8f);
        gl.glVertex2f(resultVertexE.x, resultVertexE.y);
        gl.glEnd();

        if (result.isLabelDisplay()) {
            textRenderer.beginRendering((int) (2 * coordinateSize * fontScale), (int) (2 * coordinateSize * fontScale));

            textRenderer.draw(resultVertexE.toString(),
                    (int) ((resultVertexE.x + coordinateSize + 0.3f) * fontScale),
                    (int) ((resultVertexE.y + coordinateSize + 0.3f) * fontScale));

            textRenderer.endRendering();
        }
    }
}
