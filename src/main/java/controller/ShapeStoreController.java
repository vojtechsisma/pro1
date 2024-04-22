package controller;

import model.Shape;

import java.util.ArrayList;

public class ShapeStoreController {
    private final ArrayList<Shape> shapes;

    public ShapeStoreController() {
        shapes = new ArrayList<>();
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void remove(Shape selectedShape) {
        shapes.remove(selectedShape);
    }

    public boolean isEmpty() {
        return shapes.isEmpty();
    }

    public Shape getLastShape() {
        return shapes.get(shapes.size() - 1);
    }

    public int getSelectedShapeIndex() {
        DrawController drawController = DrawController.getInstanceOf();
        return shapes.indexOf(drawController.getSelectedShape());
    }
}
