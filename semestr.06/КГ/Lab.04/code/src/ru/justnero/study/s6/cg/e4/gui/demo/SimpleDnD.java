package ru.justnero.study.s6.cg.e4.gui.demo;

import javax.swing.*;


public class SimpleDnD extends JFrame {

    JTextField field;
    JButton button;

    public SimpleDnD() {

        setTitle("Simple Drag & Drop");

        setLayout(null);

        button = new JButton("Button");
        button.setBounds(200, 50, 90, 25);

        field = new JTextField();
        field.setBounds(30, 50, 150, 25);

        add(button);
        add(field);

        field.setDragEnabled(true);
        button.setTransferHandler(new TransferHandler("text"));


        setSize(330, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleDnD();
    }
}