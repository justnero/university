package ru.justnero.sevsu.s6.toi.e3.gui;

import javax.swing.*;

public class TableForm extends JPanel {
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JTable table1;

    public TableForm() {
        super();

        add(rootPanel);
    }

    public TableForm(JTable table) {
        super();

        table1.setModel(table.getModel());
        scrollPane.add(table);
        add(rootPanel);
    }

    public void setTable(JTable table) {
        table1.setModel(table.getModel());
        scrollPane.add(table);
    }
}
