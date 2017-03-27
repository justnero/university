package ru.justnero.study.s6.cg.e4.util;

import com.jogamp.opengl.GLAutoDrawable;

public interface Drawable {
    void display(GLAutoDrawable drawable, float coordinateSize, boolean fill);
}
