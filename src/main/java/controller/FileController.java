package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import model.*;
import model.Rectangle;
import model.Shape;

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

        if (!filePath.endsWith(".json")) {
            filePath += ".json";
        }

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

        if (!filePath.endsWith(".png")) {
            filePath += ".png";
        }

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

    public static String CSV_DELIMITER = ",";

    public static void saveCsv(String filePath) throws IOException {
        DrawController drawController = DrawController.getInstanceOf();

        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        ArrayList<Shape> shapes = drawController.getShapes();
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Shape shape : shapes) {
                writer.write(shape.toCsv() + "\n");
            }
            writer.close();
            drawController.setChangesSaved(true);
        } catch (IOException e) {
            throw new IOException("Failed to save file");
        }
    }

    public static void loadCsv(File file) throws IOException {
        DrawController drawController = DrawController.getInstanceOf();
        try {
            drawController.getShapes().clear();
            FileReader reader = new FileReader(file);
            StringBuilder sb = new StringBuilder();
            int charCode;
            while ((charCode = reader.read()) != -1) {
                sb.append((char) charCode);
            }
            reader.close();
            String[] lines = sb.toString().split("\n");
            for (String line : lines) {
                switch (line.split(CSV_DELIMITER)[0]) {
                    case "RECTANGLE" -> drawController.getShapes().add(new Rectangle().fromCsv(line));
                    case "OVAL" -> drawController.getShapes().add(new Oval().fromCsv(line));
                    case "LINE" -> drawController.getShapes().add(new Line().fromCsv(line));
                    default -> throw new IOException("Unknown shape type");
                }
            }
            drawController.onFileLoaded();
            drawController.setChangesSaved(true);
        } catch (Exception e) {
            drawController.getShapes().clear();
            throw new IOException("Failed to load file");
        }
    }

    public static String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "Unknown";
        }
    }

    public static void loadFile(File file) throws IOException {
        String fileType = getFileType(file);
        switch (fileType) {
            case "json":
                loadJson(file);
                break;
            case "csv":
                loadCsv(file);
                break;
            default:
                throw new IOException("Unsupported file type");
        }
    }
}
