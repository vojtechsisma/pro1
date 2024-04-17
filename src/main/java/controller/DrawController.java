package controller;

import model.Rectangle;
import model.Shape;
import model.ShapeTool;
import view.ShapeObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class DrawController {
    private static DrawController instance;
    private final List<ShapeObserver> observers;
    private final ArrayList<Shape> shapes;
    private Shape selectedShape = null;
    private Shape shapeToDraw = null;
    private int startX, startY, endX, endY;
    private boolean isDrawing = false;
    private Color selectedColor = null;
    private ShapeTool selectedTool = ShapeTool.RECTANGLE;

    private DrawController() {
        shapes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static DrawController getInstanceOf() {
        if (instance == null) {
            instance = new DrawController();
        }
        return instance;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void removeSelectedShape() {
        shapes.remove(selectedShape);
        selectedShape = null;
        notifyObserversShapeRemoved();
    }

    public void setSelectedShape(Shape shape) {
        selectedShape = shape;
        notifyObserversShapeSelected();
    }

    public int getSelectedShapeIndex() {
        return shapes.indexOf(selectedShape);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void drawItems(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isDrawing) {
            switch (selectedTool) {
                case RECTANGLE:
                    int width = Math.abs(endX - startX);
                    int height = Math.abs(endY - startY);
                    int x = Math.min(startX, endX);
                    int y = Math.min(startY, endY);

                    shapeToDraw.setPosX(x);
                    shapeToDraw.setPosY(y);
                    ((Rectangle) shapeToDraw).setWidth(width);
                    ((Rectangle) shapeToDraw).setHeight(height);
                    shapeToDraw.draw(g2d);
                    break;
                case OVAl:
                    break;
                case LINE:
                    break;
            }
        }

        for (Shape shape : shapes) {
            shape.draw(g2d);
        }
    }

    public void handleMousePressed(int x, int y) {
        startX = x;
        startY = y;

        Shape shapeToDraw = null;

        switch (selectedTool) {
            case RECTANGLE:
                shapeToDraw = new Rectangle(x, y, selectedColor, 0, 0);
                break;
            case OVAl:
                break;
            case LINE:
                break;
        }

        this.shapeToDraw = shapeToDraw;
        selectedShape = null;
        isDrawing = true;
    }

    int initalX, initialY;

    public boolean handleMouseSelection(int x, int y) {
        isDrawing = false;
        for (Shape shape : shapes) {
            if (shape.contains(x, y)) {
                setSelectedShape(shape);
                selectedColor = shape.getFill();
                initalX = shape.getPosX();
                initialY = shape.getPosY();
                return true;
            }
        }
        return false;
    }

    public void handleMouseReleased(int x, int y) {
        if (!isDrawing) {
            return;
        }

        endX = x;
        endY = y;
        isDrawing = false;

        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        if (width == 0 || height == 0) {
            return;
        }

        shapes.add(shapeToDraw);
        setSelectedShape(shapeToDraw);
        notifyObserversShapeAdded();
    }

    public void handleMouseDragged(int x, int y) {
        if (isDrawing) {
            shapeToDraw.setPosX(x);
            shapeToDraw.setPosY(y);
            endX = x;
            endY = y;
        }

        if (selectedShape != null) {
            selectedShape.move(x, y);
        }
    }

    private boolean shiftPressed = false;

    // TODO: add observer on shape move
    public void handleKeyPressed(int keyCode) {
        if (selectedShape == null) {
            return;
        }

        int moveStep = shiftPressed ? 10 : 1;

        switch (keyCode) {
            case VK_LEFT:
                selectedShape.move(selectedShape.getPosX() - moveStep, selectedShape.getPosY());
                break;
            case VK_UP:
                selectedShape.move(selectedShape.getPosX(), selectedShape.getPosY() - moveStep);
                break;
            case VK_RIGHT:
                selectedShape.move(selectedShape.getPosX() + moveStep, selectedShape.getPosY());
                break;
            case VK_DOWN:
                selectedShape.move(selectedShape.getPosX(), selectedShape.getPosY() + moveStep);
                break;
            case VK_DELETE:
                removeSelectedShape();
                break;
            case VK_SHIFT:
                shiftPressed = true;
                break;
        }
    }

    public void handleKeyReleased(int keyCode) {
        if (keyCode == VK_SHIFT) {
            shiftPressed = false;
        }
    }

    public void addObserver(ShapeObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversShapeAdded() {
        for (ShapeObserver observer : observers) {
            observer.shapeAdded();
        }
    }

    private void notifyObserversShapeRemoved() {
        for (ShapeObserver observer : observers) {
            observer.shapeRemoved();
        }
    }

    private void notifyObserversShapeFilled() {
        for (ShapeObserver observer : observers) {
            observer.shapeFilled();
        }
    }

    private void notifyObserversShapeSelected() {
        for (ShapeObserver observer : observers) {
            observer.shapeSelected();
        }
    }

    public void setColor(Color color) {
        this.selectedColor = color;
        if (selectedShape != null) {
            selectedShape.setFill(color);
            notifyObserversShapeFilled();
        }
    }
}
