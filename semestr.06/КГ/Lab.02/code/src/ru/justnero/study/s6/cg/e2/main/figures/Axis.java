package ru.justnero.study.s6.cg.e2.main.figures;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Axis {
    public static final TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 25));
    public final static int fontScale = 70;

    protected float halfLineWidth = 0.2718f;
    protected float coordinateSize = 10;
    protected float arrowLength = 0.6f;

    protected boolean gridOn = true;

    public Axis() {
    }

    public Axis(float coordinateSize) {
        this.coordinateSize = coordinateSize;
    }

    public void setCoordinateSize(float coordinateSize) {
        this.coordinateSize = coordinateSize;
    }

    public void setGridOn(boolean gridOn) {
        this.gridOn = gridOn;
    }

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        if (gridOn) {
            displayGrid(gl);
        }

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0, 0, 0);

        setOnPointsForAxis(gl);
        setOnPointsForArrows(gl);
        setOnPointsForDashes(gl);

        gl.glEnd();

        displayNumberLabel();
        displayAxisLabel();
    }

    protected void displayGrid(GL2 gl) {
        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glLineStipple(1, (short) 0xAAAA);
        gl.glColor3f(.5f, .5f, .5f);
        gl.glBegin(GL2.GL_LINES);

        setOnPointsForGrid(gl);

        gl.glEnd();
        gl.glColor3f(0f, 0f, 0f);
        gl.glDisable(GL2.GL_LINE_STIPPLE);
    }

    protected void setOnPointsForGrid(GL2 gl) {
        for (int i = (int) (-coordinateSize + 1); i <= coordinateSize - 1; i++) {
            if (i == 0) {
                continue;
            }

            gl.glVertex2f(-coordinateSize + 1, i);
            gl.glVertex2f(coordinateSize - 1, i);

            gl.glVertex2f(i, -coordinateSize + 1);
            gl.glVertex2f(i, coordinateSize - 1);
        }
    }

    protected void setOnPointsForAxis(GL2 gl) {
        gl.glVertex2f(-coordinateSize, 0);
        gl.glVertex2f(coordinateSize, 0);

        gl.glVertex2f(0, -coordinateSize);
        gl.glVertex2f(0, coordinateSize);
    }

    protected void setOnPointsForArrows(GL2 gl) {
        gl.glVertex2f(coordinateSize, 0);
        gl.glVertex2f(coordinateSize - arrowLength, halfLineWidth);

        gl.glVertex2f(coordinateSize, 0);
        gl.glVertex2f(coordinateSize - arrowLength, -halfLineWidth);


        gl.glVertex2f(0, coordinateSize);
        gl.glVertex2f(-halfLineWidth, coordinateSize - arrowLength);

        gl.glVertex2f(0, coordinateSize);
        gl.glVertex2f(halfLineWidth, coordinateSize - arrowLength);
    }

    protected void setOnPointsForDashes(GL2 gl) {
        for (int i = (int) (-coordinateSize); i <= coordinateSize - 1; i++) {
            if (i == 0) {
                continue;
            }
            gl.glVertex2f(0 - halfLineWidth, i);
            gl.glVertex2f(0 + halfLineWidth, i);

            gl.glVertex2f(i, 0 - halfLineWidth);
            gl.glVertex2f(i, 0 + halfLineWidth);
        }
    }

    protected void displayNumberLabel() {
        textRenderer.beginRendering((int) (2 * coordinateSize * fontScale), (int) (2 * coordinateSize * fontScale));
        textRenderer.setColor(Color.BLACK);

        for (int i = (int) (-coordinateSize + 1); i <= coordinateSize - 1; i++) {
            if (i == 0) {
                textRenderer.draw("0",
                        (int) ((coordinateSize - 0.3) * fontScale),
                        (int) ((coordinateSize - 0.5) * fontScale));
                continue;
            }

            textRenderer.draw(String.valueOf(i),
                    (int) ((coordinateSize - 3 * halfLineWidth) * fontScale),
                    (int) ((coordinateSize + i - halfLineWidth / 2f) * fontScale));

            textRenderer.draw(String.valueOf(i),
                    (int) ((coordinateSize + i - halfLineWidth / 2f) * fontScale),
                    (int) ((coordinateSize - 3 * halfLineWidth) * fontScale));
        }

        textRenderer.endRendering();
    }

    protected void displayAxisLabel() {
        textRenderer.beginRendering((int) (2 * coordinateSize * fontScale), (int) (2 * coordinateSize * fontScale));
        textRenderer.setColor(Color.BLACK);

        textRenderer.draw("X",
                (int) ((2 * coordinateSize - arrowLength) * fontScale),
                (int) ((coordinateSize + 2 * halfLineWidth) * fontScale));

        textRenderer.draw("Y",
                (int) ((coordinateSize + 2 * halfLineWidth) * fontScale),
                (int) ((2 * coordinateSize - arrowLength) * fontScale));

        textRenderer.endRendering();
    }
}
