package ru.justnero.study.sevsu.java.lab04;

import javax.swing.*;

public class DeleteFrame extends JFrame {
    private JPanel rootPanel;
    private JTextField authorF;
    private JCheckBox bySelectionF;
    private JButton deleteB;
    private JButton cancelB;

    public DeleteFrame(MainFrame mf) {
        super("Delete form | SevSU IS-21 semester.04 Java Lab.04");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        setLocationByPlatform(true);

        deleteB.addActionListener(actionEvent -> {
            if(bySelectionF.isSelected()) {
                if(mf.getSelected() >= 0) {
                    CDTableModel.getInstance().deleteRow(mf.getSelected());
                    JOptionPane.showMessageDialog(this, "Deleted 1 element", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "No element selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if(!authorF.getText().isEmpty()) {
                    long count = CDTableModel.getInstance().deleteRow(authorF.getText());
                    if(count > 0) {
                        JOptionPane.showMessageDialog(this, "Deleted "+String.valueOf(count)+" element(s)", "Success", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "No elements found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelB.addActionListener(actionEvent -> {
            setVisible(false);
        });

        bySelectionF.addActionListener(actionEvent -> {
            authorF.setEnabled(!bySelectionF.isSelected());
        });

        pack();
    }

    public void clear() {
        authorF.setText("");
    }

}
