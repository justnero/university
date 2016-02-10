package ru.justnero.study.sevsu.java.lab04;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            _log("Unexpected UIManager exception, L&F was not set\n");
            e.printStackTrace();
        }
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }

    private static void _log(Object... args) {
        StringBuilder sb = new StringBuilder();
        for(Object str : args) {
            sb.append(str);
        }
        System.out.print(sb.toString());
    }

}
