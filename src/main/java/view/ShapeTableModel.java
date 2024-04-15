package view;

import model.Shape;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ShapeTableModel extends AbstractTableModel {
    private ArrayList<Shape> shapes;
    private String[] columnNames = {"Tvar", "x", "y", "Šířka", "Výška"};

    public ShapeTableModel(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public int getRowCount() {
        return shapes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shape shape = shapes.get(rowIndex);
        return shape.getTableValues().get(columnIndex);
    }

    public void addRow() {
        fireTableRowsInserted(shapes.size() - 1, shapes.size() - 1);
    }

    public void removeRow() {
        fireTableRowsDeleted(shapes.size() - 1, shapes.size() - 1);
    }
}
