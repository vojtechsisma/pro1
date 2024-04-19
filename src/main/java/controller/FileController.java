package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import model.Shape;
import model.ShapeDeserializer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileController {

    public static void saveJson(String filePath) throws IOException {
        DrawController drawController = DrawController.getInstanceOf();
        ArrayList<Shape> shapes = drawController.getShapes();

        Gson gson = new Gson();
        String json = gson.toJson(shapes);
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(json);
            writer.close();
            drawController.setChangesSaved(true);
        } catch (IOException e) {
            throw new IOException("Failed to save file");
        }
    }

    public static void loadJson(File file) throws IOException {
        DrawController drawController = DrawController.getInstanceOf();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeDeserializer())
                .create();

        try {
            Shape[] shapes = gson.fromJson(new FileReader(file), Shape[].class);
            drawController.getShapes().clear();
            for (Shape shape : shapes) {
                drawController.getShapes().add(shape);
            }
            drawController.onFileLoaded();
            drawController.setChangesSaved(true);
        } catch (IOException | JsonParseException e) {
            throw new IOException("Failed to load file");
        }
    }

    public static void saveImage(JPanel panel, String filePath) throws IOException {
        DrawController drawController = DrawController.getInstanceOf();
        Dimension size = panel.getSize();

        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);

        panel.setBackground(Color.WHITE);
        Shape selectedShape = drawController.getSelectedShape();
        drawController.setSelectedShape(null);

        Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();

        panel.setBackground(Color.lightGray);
        drawController.setSelectedShape(selectedShape);

        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            throw new IOException("Failed to save image");
        }
    }
}
