package ru.justnero.study.s6.cg.e1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import java.awt.*;
import java.util.Vector;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class CanvasDrawer implements GLEventListener {

    private static int screenSize = 400;
    private final int n = 4, scale = 5;
    private final int xOffset = 5 * scale, yOffset = 10 * scale;
    private boolean clear = true;
    private boolean joglDraw = false;
    private boolean ddaDraw = false;

    static GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        CanvasDrawer that = new CanvasDrawer();
        glcanvas.addGLEventListener(that);
        glcanvas.setSize(screenSize, screenSize / 2);

        return glcanvas;
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1f, 1f, 0f, 0f);

        if (clear) {
            return;
        }

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, screenSize, 0, screenSize / 2, -1, 1);

        if (ddaDraw) {
            drawImageByDDA(gl);
        } else if (joglDraw) {
            drawImageByJogl(gl);
        }
    }

    private void drawImageByJogl(GL2 gl) {
        Vector<Point> result = new Vector<>();

        result.addAll(DrawMethods.circle(4 * scale, 15 * scale, 1 * scale));
        result.addAll(DrawMethods.line(5 * scale, 15 * scale, 23 * scale, 15 * scale));
        result.addAll(DrawMethods.circle(24 * scale, 15 * scale, 1 * scale));

        result.addAll(DrawMethods.circle(4 * scale, 10 * scale, 1 * scale));
        result.addAll(DrawMethods.line(5 * scale, 10 * scale, 23 * scale, 10 * scale));
        result.addAll(DrawMethods.circle(24 * scale, 10 * scale, 1 * scale));

        result.addAll(DrawMethods.line(9 * scale, 15 * scale, 9 * scale, 8 * scale));
        result.addAll(DrawMethods.key(10 * scale, 3 * scale, 2 * scale, 4 * scale, 1 * scale));
        result.addAll(DrawMethods.line(9 * scale, 2 * scale, 9 * scale, 1 * scale));

        result.addAll(DrawMethods.line(9 * scale, 1 * scale, 11 * scale, 1 * scale));
        result.addAll(DrawMethods.resistor(12 * scale, 0 * scale, 4 * scale, 2 * scale, 1 * scale));
        result.addAll(DrawMethods.line(17 * scale, 1 * scale, 19 * scale, 1 * scale));

        result.addAll(DrawMethods.line(19 * scale, 1 * scale, 19 * scale, 10 * scale));

        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(1f, 0f, 0f);
        result.forEach(point -> gl.glVertex2i(point.x + xOffset, point.y + yOffset));
        gl.glEnd();
    }

    private void drawImageByDDA(GL2 gl) {
        Vector<Point> result = new Vector<>();

        result.addAll(DrawMethods.circle(4 * scale, 15 * scale, 1 * scale));
        result.addAll(DrawMethods.line(5 * scale, 15 * scale, 23 * scale, 15 * scale));
        result.addAll(DrawMethods.circle(24 * scale, 15 * scale, 1 * scale));

        result.addAll(DrawMethods.circle(4 * scale, 10 * scale, 1 * scale));
        result.addAll(DrawMethods.line(5 * scale, 10 * scale, 23 * scale, 10 * scale));
        result.addAll(DrawMethods.circle(24 * scale, 10 * scale, 1 * scale));

        result.addAll(DrawMethods.line(9 * scale, 15 * scale, 9 * scale, 8 * scale));
        result.addAll(DrawMethods.key(10 * scale, 3 * scale, 2 * scale, 4 * scale, 1 * scale));
        result.addAll(DrawMethods.line(9 * scale, 2 * scale, 9 * scale, 1 * scale));

        result.addAll(DrawMethods.line(9 * scale, 1 * scale, 11 * scale, 1 * scale));
        result.addAll(DrawMethods.resistor(12 * scale, 0 * scale, 4 * scale, 2 * scale, 1 * scale));
        result.addAll(DrawMethods.line(17 * scale, 1 * scale, 19 * scale, 1 * scale));

        result.addAll(DrawMethods.line(19 * scale, 1 * scale, 19 * scale, 10 * scale));

        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(1f, 0f, 0f);
        result.forEach(point -> gl.glVertex2i(point.x + xOffset * 10, point.y + yOffset));
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        glAutoDrawable.getGL().getGL2().glViewport(i, i1, i2, i3);
    }

    public void setClear() {
        this.clear = true;
    }

    public void setJoglDraw() {
        this.joglDraw = true;
        clear = false;
        ddaDraw = false;
    }


    public void setDdaDraw() {
        this.ddaDraw = true;
        clear = false;
        joglDraw = false;
    }
}
