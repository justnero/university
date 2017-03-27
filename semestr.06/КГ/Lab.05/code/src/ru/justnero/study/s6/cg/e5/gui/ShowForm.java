package ru.justnero.study.s6.cg.e5.gui;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;

import javax.swing.*;

import ru.justnero.study.s6.cg.e5.main.Lab5Scene;
import ru.justnero.study.s6.cg.e5.main.Main;

public class ShowForm extends JPanel {
    protected final static float speedScale = 100f;
    private JPanel rootPanel;
    private JPanel showPanel1;
    private JPanel inputPanel;
    private JSlider sliderAxisScale;
    private JPanel sliderPanel;
    private JSlider sliderFpsRate;
    private JSlider sliderTotalTime;
    private JSlider sliderAnimationSpeed;
    private JPanel buttonPanel;
    private JButton btnClear;
    private JButton btnStart;
    private JButton btnPause;
    private JTabbedPane tabbedPaneShow;
    private JPanel showPanel2;
    private JTextField txtAParameter;
    private JTextField txtBParameter;
    private JTextField txtAParameter2;
    private JTextField txtCParameter;
    private Lab5Scene scene;
    private Main jogl;
    private GLCanvas canvas;

    public ShowForm() {
        super();

        new Thread(() -> {
            while (true) {
                canvas.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        addListeners();
        add(rootPanel);
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                new ShowForm(),
                "Лабораторная работа 5",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void createUIComponents() {
        canvas = Main.createCanvas();
        jogl = (Main) canvas.getGLEventListener(0);
        scene = jogl.getScene();

        showPanel1 = new JPanel(new GridLayout(1, 1));
        showPanel1.add(canvas);
    }

    private void addListeners() {
        btnStart.addActionListener(l -> {
            scene.setAnimation(true);

            scene.getFunction1().setA(Float.parseFloat(txtAParameter.getText()));
            scene.getFunction1().setB(Float.parseFloat(txtBParameter.getText()));
            scene.getFunction1().setC(Float.parseFloat(txtCParameter.getText()));

            scene.getFunction2().setA(Float.parseFloat(txtAParameter2.getText()));
//            scene.getFunction2().setLambda(Float.parseFloat(txtLambdaParameter.getText()));
        });

        btnPause.addActionListener(l -> {
            scene.setAnimation(false);
        });

        btnClear.addActionListener(l -> {
            scene.reset();
        });

        sliderAxisScale.addChangeListener(l -> {
            jogl.setCoordinateSize(sliderAxisScale.getValue() + 1);
            jogl.setFunctionLimits(sliderAxisScale.getValue());
            canvas.repaint();
        });

        sliderAnimationSpeed.addChangeListener(l -> {
            scene.setSpeed(sliderAnimationSpeed.getValue() / speedScale);
        });

        tabbedPaneShow.addChangeListener(l -> {
            scene.setFirstFunctionSelected(tabbedPaneShow.getSelectedIndex() == 0);
        });
    }
}
