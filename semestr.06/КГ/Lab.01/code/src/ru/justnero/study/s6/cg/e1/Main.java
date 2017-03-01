package ru.justnero.study.s6.cg.e1;

import com.jogamp.opengl.awt.GLCanvas;

import ru.justnero.study.s6.cg.e1.gui.Form;

public class Main {

    public static void main(String args[]) {
        final GLCanvas glcanvas = CanvasDrawer.createCanvas();

        final Form frame = new Form(glcanvas);
    }

}
