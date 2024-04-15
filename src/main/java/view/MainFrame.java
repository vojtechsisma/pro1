package view;

import controller.DrawController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ShapeObserver {
    ShapeTableModel tableModel;

    public MainFrame() {
        DrawController drawController = DrawController.getInstanceOf();
        drawController.addObserver(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panel = new DrawingPanel();

        Toolbar toolbar = new Toolbar();
        this.add(toolbar, BorderLayout.NORTH);

        setMinimumSize(new Dimension(500, 500));
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setFocusable(true);
        this.getContentPane().add(panel);

        Menubar menubar = new Menubar();
        this.setJMenuBar(menubar);

        ShapesTable shapesTable = new ShapesTable();
        tableModel = new ShapeTableModel(drawController.getShapes());
        shapesTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(shapesTable);
        this.add(scrollPane, BorderLayout.EAST);

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
}
