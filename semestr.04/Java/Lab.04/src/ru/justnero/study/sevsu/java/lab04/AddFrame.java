package ru.justnero.study.sevsu.java.lab04;

import javax.swing.*;

public class AddFrame extends JFrame {
    private JTextField titleF;
    private JTextField authorF;
    private JSpinner trackCountF;
    private JSpinner durationF;
    private JButton addB;
    private JButton cancelB;
    private JPanel rootPanel;

    public AddFrame() {
        super("Add form | SevSU IS-21 semester.04 Java Lab.04");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        setLocationByPlatform(true);

        trackCountF.setModel(new SpinnerNumberModel(1,1,100,1));
        durationF.setModel(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));

        addB.addActionListener(actionEvent -> {
            if(titleF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(authorF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Author can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            CDTableModel.getInstance().addRow(new CD(titleF.getText(), authorF.getText(), (int) trackCountF.getValue(), (int) durationF.getValue()));
            JOptionPane.showMessageDialog(this, "Element added", "Success", JOptionPane.INFORMATION_MESSAGE);

            setVisible(false);
        });

        cancelB.addActionListener(actionEvent -> {
            setVisible(false);
        });

        pack();
    }

    public void clear() {
        titleF.setText("");
        authorF.setText("");
        trackCountF.setValue(1);
        durationF.setValue(0);
    }
}
