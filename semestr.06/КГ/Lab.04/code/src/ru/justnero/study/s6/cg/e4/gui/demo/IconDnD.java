package ru.justnero.study.s6.cg.e4.gui.demo;

import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.*;


public class IconDnD extends JFrame {


    public IconDnD() {

        setTitle("Icon Drag & Drop");

        JPanel panel = new JPanel(new GridLayout(3, 3));
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 200, 250));

        ImageIcon icon1 = new ImageIcon("sad.png");
        ImageIcon icon2 = new ImageIcon("plain.jpg");
        ImageIcon icon3 = new ImageIcon("crying.png");

        JButton button = new JButton(icon2);
        button.setPreferredSize(new Dimension(100, 100));
        button.setFocusable(false);

        JLabel label1 = new JLabel(icon1, JLabel.CENTER);
//        label1.setText("labale1");
        JLabel label2 = new JLabel(icon3, JLabel.CENTER);
//        label2.setText("labale2");

        MouseListener listener = new DragMouseAdapter();
        label1.addMouseListener(listener);
        label2.addMouseListener(listener);

        TransferHandler transferHandler = new TransferHandler("icon");
        transferHandler.setDragImage(new ImageIcon("plain.jpg").getImage());

        label1.setTransferHandler(transferHandler);
        button.setTransferHandler(transferHandler);
        label2.setTransferHandler(transferHandler);

        panel.add(label1);
        panel.add(button);
        panel.add(label2);
        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new IconDnD();
    }
}