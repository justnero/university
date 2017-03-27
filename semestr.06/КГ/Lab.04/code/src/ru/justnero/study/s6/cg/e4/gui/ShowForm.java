package ru.justnero.study.s6.cg.e4.gui;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.util.function.Supplier;

import javax.swing.*;

import ru.justnero.study.s6.cg.e2.gui.Animation;
import ru.justnero.study.s6.cg.e2.main.figures.AnimableQuad;
import ru.justnero.study.s6.cg.e4.Main;
import ru.justnero.study.s6.cg.e4.main.ClipBox;
import ru.justnero.study.s6.cg.e4.main.Lab4Scene;
import ru.justnero.study.s6.cg.e4.main.Labyrinth;
import ru.justnero.study.s6.cg.e4.main.Parallelogram;
import ru.justnero.study.s6.cg.e4.main.Quad;
import ru.justnero.study.s6.cg.e4.main.Trapeze;
import ru.justnero.study.s6.cg.e4.util.TransformMatrix;
import ru.justnero.study.s6.cg.e4.util.Vector2D;

public class ShowForm extends JPanel {
    protected static final float speedScale = 400f;
    protected static final float sizeScale = 1000f;
    protected final TransformMatrix transformationMatrix = new TransformMatrix();
    protected final AnimableQuad temp = new AnimableQuad();
    protected final AnimableQuad origin = new AnimableQuad();
    protected final Quad tempQuad = new Quad();
    protected Animation animation;
    protected Animation animationVertex;
    protected Animation animationRotate;
    DropTargetListener dropTargetListener;
    float globalSpeedScale = 10e3f;
    private JPanel rootPanel;
    private JPanel showPanel;
    private JPanel inputPanel;
    private JPanel checkBoxPanel;
    private JPanel buttonPanel;
    private JButton btnStart;
    private JButton btnClear;
    private JCheckBox checkBoxFill;
    private JButton btnPause;
    private JTabbedPane tabbedPane1;
    private JSlider sliderGlobaalSpeed;
    private JLabel lblRhombus;
    private JLabel lblTrapeze;
    private JColorChooser colorChooser;
    private JSlider sliderMoveSpeed;
    private JSlider sliderAngle;
    private JSlider sliderSize;
    private JLabel lblLabyrinth;
    private JLabel lblClipBox;


    //    protected final AnimableVector3f vertexE;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private ButtonGroup buttonGroup2 = new ButtonGroup();
    private Lab4Scene scene;
    private Main jogl;
    private GLCanvas canvas;
    //    private ImageIcon parallelogram;
    private ImageIcon parallelogram = new ImageIcon("resource/parallelogram.png");
    private ImageIcon trapeze = new ImageIcon("resource/trapeze.png");
    private ImageIcon labyrinth = new ImageIcon("resource/labyrinth.png");
    private ImageIcon clipBox = new ImageIcon("resource/clipBox.png");

    public ShowForm() {
        super();

        colorChooser.setColor(Color.white);

//        TransferHandler transferHandler = new TransferHandler("icon");
//        transferHandler.setDragImage(new ImageIcon("plain.jpg").getImage());

        int iconSize = 64;

        Image temp = parallelogram.getImage();
        temp = temp.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        parallelogram.setImage(temp);

        temp = trapeze.getImage();
        temp = temp.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
        trapeze.setImage(temp);

        labyrinth.setImage(labyrinth.getImage().getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH));
        clipBox.setImage(clipBox.getImage().getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH));

        lblRhombus.setIcon(parallelogram);
        lblTrapeze.setIcon(trapeze);
        lblLabyrinth.setIcon(labyrinth);
        lblClipBox.setIcon(clipBox);


        dropTargetListener = new DropTargetListener(showPanel, scene);

        Supplier<Vector2D> velocityGetter = () -> {
            int angle = sliderAngle.getValue();
            int speed = sliderMoveSpeed.getValue();

            Vector2D velocity = new Vector2D(
                    (float) Math.cos(Math.toRadians(angle)),
                    (float) Math.sin(Math.toRadians(angle)));

            velocity.multiply(speed / speedScale);

            return velocity;
        };

        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(lblRhombus.getParent(),
                DnDConstants.ACTION_COPY, new DragListener(
                        (this::createQuad), Figure.PARALLELOGRAM, velocityGetter));

        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(lblTrapeze.getParent(),
                DnDConstants.ACTION_COPY, new DragListener(
                        (this::createQuad), Figure.TRAPEZE, velocityGetter));

        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(lblLabyrinth.getParent(),
                DnDConstants.ACTION_COPY, new DragListener(
                        (this::createQuad), Figure.LABYRINTH, velocityGetter));

        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(lblClipBox.getParent(),
                DnDConstants.ACTION_COPY, new DragListener(
                        (this::createQuad), Figure.CLIP_BOX, velocityGetter));

        addListeners();
        add(rootPanel);
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                new ShowForm(),
                "Лабораторная работа 4",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void createUIComponents() {
        canvas = Main.createCanvas();
        jogl = (Main) canvas.getGLEventListener(0);
        scene = jogl.getScene();


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


        showPanel = new JPanel(new GridLayout(1, 1));
        showPanel.add(canvas);
    }

    protected Quad createQuad(Figure figure) {
        Quad resultFigure = null;

        switch (figure) {
            case PARALLELOGRAM:
                resultFigure = new Parallelogram();
                break;
            case TRAPEZE:
                resultFigure = new Trapeze();
                break;
            case LABYRINTH:
                resultFigure = new Labyrinth();
                break;
            case CLIP_BOX:
                resultFigure = new ClipBox();
                break;
        }

        transformationMatrix.scale(sliderSize.getValue() / sizeScale, sliderSize.getValue() / sizeScale,
                ((figure == Figure.LABYRINTH || figure == Figure.CLIP_BOX) ? 0.5f : 1f));

        resultFigure.setResult(tempQuad);
        resultFigure.transform(transformationMatrix);
        resultFigure.applyChange();

        resultFigure.setColor(colorChooser.getColor());

        return resultFigure;
    }

    private void addListeners() {
        btnStart.addActionListener(l -> {
            scene.setAnimation(true);
        });

        btnPause.addActionListener(l -> {
            scene.setAnimation(false);
        });

        btnClear.addActionListener(l -> {
            scene.clear();
        });

        sliderGlobaalSpeed.addChangeListener(l -> {
            scene.getGlobalParameters().value = sliderGlobaalSpeed.getValue() / globalSpeedScale;
        });

        checkBoxFill.addChangeListener(l -> {
            scene.getGlobalParameters().fill = checkBoxFill.isSelected();
        });
    }

    public enum Figure {
        PARALLELOGRAM, TRAPEZE, CLIP_BOX, LABYRINTH
    }
}
