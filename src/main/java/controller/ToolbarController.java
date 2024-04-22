package controller;

import model.ShapeTool;

import javax.swing.*;
import java.awt.*;

public class ToolbarController {
    DrawController drawController = DrawController.getInstanceOf();

    public void removeSelectedShape() {
        if (drawController.getSelectedShape() != null)
            DrawController.getInstanceOf().removeSelectedShape();
        else {
            JOptionPane.showMessageDialog(null, "Není vybrán žádný tvar!", "Chyba", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setColor(Color color) {
        drawController.setColor(color);
    }

    public void setRectangleTool() {
        drawController.setTool(ShapeTool.RECTANGLE);
    }

    public void setOvalTool() {
        drawController.setTool(ShapeTool.OVAL);
    }

    public void setLineTool() {
        drawController.setTool(ShapeTool.LINE);
    }
}
