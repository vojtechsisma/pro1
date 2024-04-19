package controller;

import model.ShapeTool;

public class MenuController {
    DrawController drawController = DrawController.getInstanceOf();
    public void setRectangleTool() {
        drawController.setTool(ShapeTool.RECTANGLE);
    }

    public void setOvalTool() {
        drawController.setTool(ShapeTool.OVAl);
    }

    public void setLineTool() {
        drawController.setTool(ShapeTool.LINE);
    }

    public void saveJson() {
        FileController.saveJson();
    }

    public void loadJson() {
        FileController.loadJson();
    }
}
