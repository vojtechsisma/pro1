package view;

import controller.DrawController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements ShapeObserver {
    ShapeTableModel tableModel;
    ShapesTable shapesTable;

    public MainFrame() {
        DrawController drawController = DrawController.getInstanceOf();
        drawController.addObserver(this);
        setTitle("Malování");
        setMinimumSize(new Dimension(900, 900));

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!drawController.isChangesSaved()) {
                    int result = JOptionPane.showConfirmDialog(MainFrame.this, "Máte neuložené změny, opravdu chcete program ukončit?", "Neuložené změny", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }

        });

        JPanel panel = new DrawingPanel();

        Toolbar toolbar = new Toolbar();
        this.add(toolbar, BorderLayout.NORTH);

        setMinimumSize(new Dimension(500, 500));
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setFocusable(true);
        this.getContentPane().add(panel);

        Menubar menubar = new Menubar(panel);
        this.setJMenuBar(menubar);

        shapesTable = new ShapesTable();
        tableModel = new ShapeTableModel(drawController.getShapes());
        shapesTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(shapesTable);
        this.add(scrollPane, BorderLayout.EAST);

        StatusBar statusBar = new StatusBar();
        this.add(statusBar, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void shapeAdded() {
        tableModel.addRow();
    }

    @Override
    public void shapeRemoved() {
        tableModel.removeRow();
    }

    @Override
    public void shapeFilled() {
        tableModel.fireTableDataChanged();
    }

    @Override
    public void shapeSelected() {
        DrawController drawController = DrawController.getInstanceOf();
        int index = drawController.getSelectedShapeIndex();
        if (index != -1 && index < tableModel.getRowCount()) {
            shapesTable.setRowSelectionInterval(index, index);
            return;
        }
        shapesTable.clearSelection();
    }

    @Override
    public void shapeMoved() {
        tableModel.fireTableDataChanged();
        shapeSelected();
    }
}
