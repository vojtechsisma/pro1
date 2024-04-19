package controller;

import model.ShapeTool;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MenuController {
    DrawController drawController = DrawController.getInstanceOf();
    public void setRectangleTool() {
        drawController.setTool(ShapeTool.RECTANGLE);
    }

    public void setOvalTool() {
        drawController.setTool(ShapeTool.OVAL);
    }

    public void setLineTool() {
        drawController.setTool(ShapeTool.LINE);
    }

    public void saveJson(String filePath) throws IOException {
        FileController.saveJson(filePath);
    }

    public void loadJson(File file) throws IOException {
        FileController.loadJson(file);
    }

    public void saveImage(JPanel panel, String filePath) throws IOException {
        FileController.saveImage(panel, filePath);
    }
}
