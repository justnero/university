package ru.justnero.study.s6.cg.e2.gui;

import javax.swing.table.AbstractTableModel;

import ru.justnero.study.s6.cg.e2.main.Matrix3f;

public class TableModelMatrix3f extends AbstractTableModel {
    protected Matrix3f transformationMatrix;

    private float[] temp = new float[3];

    public TableModelMatrix3f(Matrix3f transformationMatrix) {
        this.transformationMatrix = transformationMatrix;
    }


    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        transformationMatrix.getRow(rowIndex, temp);
        return (int) temp[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        float tempValue;

        tempValue = Float.parseFloat(String.valueOf(aValue));
        transformationMatrix.setValue(tempValue, rowIndex, columnIndex);
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
