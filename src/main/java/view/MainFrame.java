package view;

import controller.DrawController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ShapeObserver {
    ShapeTableModel tableModel;

    public MainFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panel = new DrawingPanel();

        Toolbar toolbar = new Toolbar();
        this.add(toolbar, BorderLayout.NORTH);

        ((DrawingPanel) panel).addObserver(this);
        panel.setPreferredSize(new Dimension(400, 800));
        this.getContentPane().add(panel);

        Menubar menubar = new Menubar();
        this.setJMenuBar(menubar);

        ShapesTable shapesTable = new ShapesTable();
        tableModel = new ShapeTableModel(DrawController.getInstanceOf().shapes);
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
}
