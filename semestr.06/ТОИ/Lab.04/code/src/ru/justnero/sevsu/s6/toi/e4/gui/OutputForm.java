package ru.justnero.sevsu.s6.toi.e4.gui;

import java.awt.*;

import javax.swing.*;

public class OutputForm extends JPanel {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel panelForAnalysis;
    private JPanel panelForAbstracting;
    private JTextPane textPaneForInput;
    private JTextPane textPaneForAbstract;

    public OutputForm(ru.justnero.sevsu.s6.toi.e3.gui.OutputForm outputForm, String inputText, String resultText) {
        super();

        panelForAnalysis.add(outputForm);

        textPaneForInput.setText(inputText);
        textPaneForAbstract.setText(resultText);

        textPaneForAbstract.setCaretPosition(0);
        textPaneForInput.setCaretPosition(0);

        add(rootPanel);
    }

    private void createUIComponents() {
        panelForAnalysis = new JPanel(new GridLayout(1, 1));
    }
}
