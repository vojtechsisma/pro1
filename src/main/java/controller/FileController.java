package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Shape;
import model.ShapeAdapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileController {

    public static void saveJson() {
        DrawController drawController = DrawController.getInstanceOf();
        ArrayList<Shape> shapes = drawController.getShapes();

        Gson gson = new Gson();
        String json = gson.toJson(shapes);
        try {
            FileWriter writer = new FileWriter("shapes.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadJson() {
        DrawController drawController = DrawController.getInstanceOf();
        Gson gson = new GsonBuilder().registerTypeAdapter(Shape.class, new ShapeAdapter())
                .create();

        try {
            Shape[] shapes = gson.fromJson(new FileReader("shapes.json"), Shape[].class);
            for (Shape shape : shapes) {
                drawController.getShapes().add(shape);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
