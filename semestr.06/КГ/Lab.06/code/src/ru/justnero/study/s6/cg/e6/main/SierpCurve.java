package ru.justnero.study.s6.cg.e6.main;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import ru.justnero.study.s6.cg.e2.main.figures.Vertex3f;

public class SierpCurve {
    private float x, y;
    private int iterationNumber;
    private List<Vertex3f> vertex3fs = new ArrayList<>();
    private float dist0 = 128, dist;

    SierpCurve(float size) {
        dist0 = size / 4.0f;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    void draw(GL2 gl) {
        vertex3fs.clear();
        sierp(iterationNumber);

        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_LINE_LOOP);

        vertex3fs.forEach(vertex3f -> gl.glVertex2f(vertex3f.x, vertex3f.y));

        gl.glEnd();
    }

    private void sierp(int level) {
        dist = dist0;
        for (int i = level; i > 0; i--)
            dist /= 2;
        x = dist * 2;
        y = dist;
        vertex3fs.add(new Vertex3f(x, y));

        sierpA(level); // start recursion
        lineRel('X', +dist, +dist);
        sierpB(level); // start recursion
        lineRel('X', -dist, +dist);
        sierpC(level); // start recursion
        lineRel('X', -dist, -dist);
        sierpD(level); // start recursion
        lineRel('X', +dist, -dist);
    }

    private void sierpA(int level) {
        if (level > 0) {
            sierpA(level - 1);
            lineRel('A', +dist, +dist);
            sierpB(level - 1);
            lineRel('A', +2 * dist, 0);
            sierpD(level - 1);
            lineRel('A', +dist, -dist);
            sierpA(level - 1);
        }
    }

    private void sierpB(int level) {
        if (level > 0) {
            sierpB(level - 1);
            lineRel('B', -dist, +dist);
            sierpC(level - 1);
            lineRel('B', 0, +2 * dist);
            sierpA(level - 1);
            lineRel('B', +dist, +dist);
            sierpB(level - 1);
        }
    }

    private void sierpC(int level) {
        if (level > 0) {
            sierpC(level - 1);
            lineRel('C', -dist, -dist);
            sierpD(level - 1);
            lineRel('C', -2 * dist, 0);
            sierpB(level - 1);
            lineRel('C', -dist, +dist);
            sierpC(level - 1);
        }
    }

    private void sierpD(int level) {
        if (level > 0) {
            sierpD(level - 1);
            lineRel('D', +dist, -dist);
            sierpA(level - 1);
            lineRel('D', 0, -2 * dist);
            sierpC(level - 1);
            lineRel('D', -dist, -dist);
            sierpD(level - 1);
        }
    }

    private void lineRel(char s, float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
        vertex3fs.add(new Vertex3f(x, y));
    }

}
