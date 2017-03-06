package ru.justnero.study.s6.cg.e2.gui;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import ru.justnero.study.s6.cg.e2.main.Lab2Scene;
import ru.justnero.study.s6.cg.e2.main.Main;
import ru.justnero.study.s6.cg.e2.main.Matrix3f;
import ru.justnero.study.s6.cg.e2.util.animation.SimpleAnimation;

public class ShowForm extends JPanel {
    protected final Matrix3f transformationMatrix = new Matrix3f();
    //    protected final Matrix3f currentResult = new Matrix3f();
    protected Animation animation;
    private JPanel rootPanel;
    private JPanel showPanel;
    private JPanel inputPanel;
    private JPanel checkBoxPanel;
    private JCheckBox checkBoxAnimation;
    private JCheckBox checkBoxGrid;
    private JCheckBox checkBoxLabels;
    private JPanel buttonPanel;
    private JButton btnDraw;
    private JButton btnOrigin;
    private JSlider sliderAxisScale;
    private JPanel sliderPanel;
    private JPanel panelForMatrix;
    private JTable tableBaseMatrix;
    private JTable tableTransformMatrix;
    private JTable tableResultMatrix;
    private JSlider sliderFpsRate;
    private JSlider sliderTotalTime;
    private JPanel panelForAnimation;
    private Lab2Scene scene;
    private Main jogl;
    private GLCanvas canvas;


    public ShowForm() {
        super();

        transformationMatrix.setIdentity();

        tableBaseMatrix.setModel(new QuadTableModel(scene.getBase()));
        tableTransformMatrix.setModel(new TableModelMatrix3f(transformationMatrix));
        tableResultMatrix.setModel(new QuadTableModel(scene.getResult()));

        animation = new Animation(transformationMatrix, scene.getBase(),
                () -> canvas.repaint(),
                () -> ((AbstractTableModel) tableResultMatrix.getModel()).fireTableDataChanged());

        addListeners();
        add(rootPanel);
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                new ShowForm(),
                "Лабораторная работа 2",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void addListeners() {
        sliderAxisScale.addChangeListener(l -> {
            jogl.setCoordinateSize(sliderAxisScale.getValue() + 1);
            canvas.repaint();

            ((AbstractTableModel) tableResultMatrix.getModel()).fireTableDataChanged();
            tableResultMatrix.repaint();
        });

        checkBoxAnimation.addChangeListener(l -> {
            sliderFpsRate.setEnabled(checkBoxAnimation.isSelected());
            sliderTotalTime.setEnabled(checkBoxAnimation.isSelected());
        });

        checkBoxGrid.addChangeListener(l -> {
            scene.getAxis().setGridOn(checkBoxGrid.isSelected());
            canvas.repaint();
        });

        checkBoxLabels.addChangeListener(l -> {
            scene.getResult().setLabelDisplay(checkBoxLabels.isSelected());
            canvas.repaint();
        });

        btnDraw.addActionListener(l -> {
            if (checkBoxAnimation.isSelected()) {
                btnDraw.setEnabled(false);

                scene.getBase().addAnimation(new SimpleAnimation(transformationMatrix, frameCount()));

                animation.setFpsRate(sliderFpsRate.getValue());
                animation.setTotalTime(sliderTotalTime.getValue());

                Thread work = new Thread(animation);
                work.start();

                new Thread(() -> {
                    while (work.isAlive()) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    btnDraw.setEnabled(true);
                }).start();
            } else {
                scene.transform(transformationMatrix);
                canvas.repaint();
            }
        });

        btnOrigin.addActionListener(l -> {
            transformationMatrix.setIdentity();
            ((AbstractTableModel) tableTransformMatrix.getModel()).fireTableDataChanged();
            ((AbstractTableModel) tableResultMatrix.getModel()).fireTableDataChanged();

            scene.transform(transformationMatrix);
            canvas.repaint();
//            dispose();
        });
    }

    private void createUIComponents() {
        canvas = Main.createCanvas();
        jogl = (Main) canvas.getGLEventListener(0);
        scene = (Lab2Scene) jogl.getScene();

        showPanel = new JPanel(new BorderLayout());
        showPanel.add(canvas);
    }

//    public void dispose() {
//        canvas.removeGLEventListener(jogl);
//        canvas.destroy();
//    }

    protected int frameCount() {
        return frameCount(sliderFpsRate.getValue(), sliderTotalTime.getValue());
    }

    protected int frameCount(int fpsRate, int totalTime) {
        return fpsRate * totalTime / 1000;
    }
}
