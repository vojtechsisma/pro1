package controller;

import javax.swing.*;

public class ToolbarController {
    DrawController drawController = DrawController.getInstanceOf();

    public void removeSelectedShape() {
        if (drawController.getSelectedShape() != null)
            DrawController.getInstanceOf().removeSelectedShape();
        else {
            JOptionPane.showMessageDialog(null, "Není vybrán žádný tvar!", "Chyba", JOptionPane.WARNING_MESSAGE);
        }
    }
}
