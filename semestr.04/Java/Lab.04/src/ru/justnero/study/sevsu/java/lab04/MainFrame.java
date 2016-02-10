package ru.justnero.study.sevsu.java.lab04;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private JPanel rootPanel;
    private JButton addB;
    private JButton deleteB;
    private JButton loadB;
    private JButton saveB;
    private JScrollPane scrollPane;
    private JTable dataTable;

    private AddFrame    addFrame;
    private DeleteFrame deleteFrame;

    public MainFrame() {
        super("SevSU IS-21 semester.04 Java Lab.04");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setLocationByPlatform(true);

        addFrame    = new AddFrame();
        deleteFrame = new DeleteFrame(this);

        dataTable.setModel(CDTableModel.getInstance());
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.getTableHeader().setReorderingAllowed(false);

        addB.addActionListener(actionEvent -> {
            if(!addFrame.isVisible()) {
                addFrame.setLocationRelativeTo(this);
                addFrame.clear();
                addFrame.setVisible(true);
            }
        });

        deleteB.addActionListener(actionEvent -> {
            if(!deleteFrame.isVisible()) {
                deleteFrame.setLocationRelativeTo(this);
                deleteFrame.clear();
                deleteFrame.setVisible(true);
            }
        });

        loadB.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int res = fileChooser.showDialog(this, "Load from file");
            if(res == JFileChooser.APPROVE_OPTION) {
                CDTableModel.getInstance().read(fileChooser.getSelectedFile());
            }
        });

        saveB.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int res = fileChooser.showDialog(this, "Save to file");
            if(res == JFileChooser.APPROVE_OPTION) {
                CDTableModel.getInstance().write(fileChooser.getSelectedFile());
            }
        });

        dataTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                CDTableModel.getInstance().sort(dataTable.columnAtPoint(mouseEvent.getPoint()));
            }
        });
        dataTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if((keyEvent.getKeyCode() == KeyEvent.VK_DELETE) && (dataTable.getSelectedRow() >= 0)) {
                    CDTableModel.getInstance().deleteRow(dataTable.getSelectedRow());
                }
            }
        });

        pack();
    }

    public int getSelected() {
        return dataTable.getSelectedRow();
    }

}
