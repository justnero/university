package ru.justnero.study.s6.cg.e2.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import javax.swing.*;

import ru.justnero.study.s6.cg.e2.util.Scene;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;


public class Main implements GLEventListener {
    protected static int screenSize = 600;
    protected float coordinateSize = 11;

    protected Lab2Scene scene = new Lab2Scene();

    public static GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Main b = new Main();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(screenSize, screenSize);

        return glcanvas;
    }

    public static void main(String[] args) {
        final GLCanvas glcanvas = createCanvas();

        final JFrame frame = new JFrame("Basic Frame");

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        frame.repaint();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Lab2Scene scene) {
        this.scene = scene;
    }

    public float getCoordinateSize() {
        return coordinateSize;
    }

    public void setCoordinateSize(float coordinateSize) {
        this.coordinateSize = coordinateSize;
        scene.setCoordinateSize(coordinateSize);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1f, 1f, 1f, 1f);

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-coordinateSize, coordinateSize, -coordinateSize, coordinateSize, -1, 1);

        scene.setCoordinateSize(coordinateSize);
        scene.display(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        glAutoDrawable.getGL().getGL2().glViewport(i, i1, i2, i3);
//        glAutoDrawable.getGL().getGL2().glViewport(0, 0, 600, 600);
    }
}