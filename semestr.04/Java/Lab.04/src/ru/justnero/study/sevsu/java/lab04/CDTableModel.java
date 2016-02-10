package ru.justnero.study.sevsu.java.lab04;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;

public class CDTableModel extends AbstractTableModel {

    protected static CDTableModel instance = null;

    protected final List<CD> list;

    protected int sortField = -1;
    protected int sortOrder = -1;

    protected final String[] names = {"Title", "Author", "Track count", "Duration"};

    public static CDTableModel getInstance() {
        return instance == null ? (instance = new CDTableModel()) : instance;
    }

    protected CDTableModel() {
        list = new ArrayList<>();
    }

    public void read(File file) {
        sortField = -1;
        list.clear();
        try(DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int count = dis.readInt();
            for(int i=0;i<count;i++) {
                list.add(CD.read(dis));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public void write(File file) {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(list.size());
            for(CD cd : list) {
                cd.write(dos);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort(int j) {
        Comparator<CD> comp = new CD.Comp(j);
        if(sortField == j) {
            sortOrder = 1 - sortOrder;
        } else {
            sortField = j;
            sortOrder = 0;
        }
        if(sortOrder == 1) {
            comp = comp.reversed();
        }
        Collections.sort(list,comp);

        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public void addRow(CD cd) {
        list.add(cd);
        sortField = -1;

        fireTableDataChanged();
        fireTableStructureChanged();
    }

    public void deleteRow(int i) {
        list.remove(i);

        fireTableDataChanged();
    }

    public long deleteRow(String author) {
        long count = list.stream().filter(cd -> cd.getAuthor().equalsIgnoreCase(author)).count();

        if(count > 0) {
            list.removeIf(cd -> cd.getAuthor().equals(author));
            fireTableDataChanged();
        }
        return count;
    }

    public void updateRow(int i, CD cd) {
        list.set(i,cd);
        sortField = -1;

        fireTableDataChanged();
        fireTableStructureChanged();
    }

    @Override
    public void setValueAt(Object value, int i, int j) {
        CD cd = list.get(i);
        switch(j) {
            case 0:
                cd.setTitle(String.valueOf(value));
                break;
            case 1:
                cd.setAuthor(String.valueOf(value));
                break;
            case 2:
                cd.setTrackCount(Integer.valueOf(String.valueOf(value)));
                break;
            case 3:
                cd.setDuration(Integer.valueOf(String.valueOf(value)));
                break;
        }
    }

    @Override
    public boolean isCellEditable(int i, int j) {
        return true;
    }

    @Override
    public String getColumnName(int j) {
        if(sortField == j) {
            return names[j].concat(" ").concat(sortOrder == 1 ? "▲" : "▼");
        } else {
            return names[j];
        }
    }

    @Override
    public Class<?> getColumnClass(int j) {
        switch(j) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int j) {
        if(i >= list.size()) {
            return null;
        }
        CD cd = list.get(i);
        switch(j) {
            case 0:
                return cd.getTitle();
            case 1:
                return cd.getAuthor();
            case 2:
                return cd.getTrackCount();
            case 3:
                return cd.getDuration();
        }
        return null;
    }

}
