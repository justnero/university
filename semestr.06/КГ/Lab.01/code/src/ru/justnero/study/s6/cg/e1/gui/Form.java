package ru.justnero.study.s6.cg.e1.gui;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;

import javax.swing.*;

import ru.justnero.study.s6.cg.e1.CanvasDrawer;

public class Form extends JFrame {
    private JPanel rootPanel;
    private JPanel canvasPanel;
    private JPanel controlsPanel;
    private JPanel optionsPanel;
    private JButton drawBtn;
    private JButton cleanBtn;
    private JRadioButton optionCanvas;
    private JRadioButton optionAlgo;

    private GLCanvas canvas;

    public Form(GLCanvas canvas) {
        super();
        this.canvas = canvas;
        canvasPanel.setLayout(new GridLayout(1, 1));
        canvasPanel.add(this.canvas);

        addListeners();

        setContentPane(rootPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        repaint();
    }

    private void addListeners() {
        drawBtn.addActionListener(e -> {
            if (optionCanvas.isSelected()) {
                ((CanvasDrawer) canvas.getGLEventListener(0)).setJoglDraw();
            } else {
                ((CanvasDrawer) canvas.getGLEventListener(0)).setDdaDraw();
            }
            canvas.repaint();
        });

        cleanBtn.addActionListener(e -> {
            ((CanvasDrawer) canvas.getGLEventListener(0)).setClear();
            canvas.repaint();
        });
    }
}
