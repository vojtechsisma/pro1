package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import model.Shape;
import model.ShapeDeserializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
}
