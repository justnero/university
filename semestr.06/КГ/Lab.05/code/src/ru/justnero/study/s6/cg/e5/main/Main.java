package ru.justnero.study.s6.cg.e5.main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class Main extends ru.justnero.study.s6.cg.e2.main.Main {
    protected Lab5Scene scene = new Lab5Scene();

    public void setScene(Lab5Scene scene) {
        this.scene = scene;
    }

    @Override
    public Lab5Scene getScene() {
        return this.scene;
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

    public Main() {
        setScene(this.scene);
    }

    public static GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Main b = new Main();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(screenSize, screenSize);

        return glcanvas;
    }

    public void setFunctionLimits(float value) {
        scene.setFunctionLimits(value);
    }
}
