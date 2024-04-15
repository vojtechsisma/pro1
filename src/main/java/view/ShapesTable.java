package view;

import controller.DrawController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShapesTable extends JTable {
    public ShapesTable() {
        super();
        this.setFillsViewportHeight(true);
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    handleSelection();
                }
            }
        });
    }

    void handleSelection() {
        int selectedRow = getSelectedRow();
        if (selectedRow != -1) {
            DrawController.getInstanceOf().selectedShape = DrawController.getInstanceOf().shapes.get(selectedRow);
        }
    }
}
