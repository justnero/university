package ru.justnero.study.s6.cg.e4.main;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import ru.justnero.study.s6.cg.e3.main.Lab3Scene;

public class Main extends ru.justnero.study.s6.cg.e2.main.Main {
    protected Lab3Scene scene = new Lab3Scene();
//    protected final Vertex3f vertexE = new Vertex3f();


//    public Vertex3f getVertexE() {
//        return vertexE;
//    }


    public Main() {
        setScene(scene);
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

    @Override
    public Lab3Scene getScene() {
        return scene;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);

//        final GL2 gl = drawable.getGL().getGL2();

//        gl.glBegin(GL2.GL_POINTS);
//        gl.glColor3f(0.1f, 0.1f, 0.8f);
//        gl.glVertex2f(vertexE.x, vertexE.y);
//        gl.glEnd();
    }
}
