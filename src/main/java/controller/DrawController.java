package controller;

import model.Rectangle;
import model.Shape;
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
    private int startX, startY, endX, endY;
    private boolean isDrawing = false;

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
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void drawItems(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isDrawing) {
            drawShapePreview(g);
        }

        for (Shape shape : shapes) {
            if (shape.isSelected()) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLACK);
            }

            if (shape instanceof Rectangle) {
                drawRectangle((Rectangle) shape, g);
            }
        }
    }

    private void drawRectangle(Rectangle rectangle, Graphics g) {
        g.drawRect(rectangle.getPosX(), rectangle.getPosY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public void handleMousePressed(int x, int y) {
        startX = x;
        startY = y;

        setSelectedShape(null);
        isDrawing = true;
    }

    int initalX, initialY;

    public boolean handleMouseSelection(int x, int y) {
        isDrawing = false;
        for (Shape shape : shapes) {
            if (shape.contains(x, y)) {
                selectedShape = shape;
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

        Rectangle rectangle = new Rectangle(Math.min(startX, endX), Math.min(startY, endY), width, height);
        shapes.add(rectangle);
        notifyObserversShapeAdded();
    }

    public void handleMouseDragged(int x, int y) {
        if (isDrawing) {
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

    public void drawShapePreview(Graphics g) {
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        g.drawRect(x, y, width, height);
    }
}
