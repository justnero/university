package ru.justnero.univercity.semestr03.oop.lab08;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    
    public static ArrayList<TFile> data = new ArrayList<>();
    public static Frame frameF;
    public static Add addF;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace(System.err);
        }
        frameF = new Frame();
        EventQueue.invokeLater(() -> {
            frameF.setVisible(true);
        });
    }
    
    public static void add() {
        if(addF == null) {
            addF = new Add();
        }
        EventQueue.invokeLater(() -> {
            if(!addF.isShowing()) {
                addF.fill();
            }
            addF.setVisible(true);
        });
    }
    
    public static void add(TFile file) {
        data.add(file);
        fillList();
    }
    
    public static void delete(int i) {
        data.remove(i);
        fillList();
    }
    
    public static void load() {
        data.clear();
        try(DataInputStream in = new DataInputStream(new FileInputStream("data.bin"))) {
            int n = in.readInt();
            for(int i=0;i<n;i++) {
                data.add(TFile.read(in));
            }
        } catch(IOException ex) {
            ex.printStackTrace(System.err);
        }
        fillList();
    }
    
    public static void save() {
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("data.bin"))) {
            out.writeInt(data.size());
            data.stream().forEach((d) -> {
                try {
                    d.write(out);
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            });
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    private static void fillList() {
        DefaultListModel m = new DefaultListModel();
        data.stream().forEach((d) -> {
            m.addElement(d.toString());
        });
        frameF.list.setModel(m);
        frameF.list.setSelectedIndex(-1);
    }
    
}
