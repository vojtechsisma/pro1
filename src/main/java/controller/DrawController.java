package controller;

import model.*;
import model.Rectangle;
import model.Shape;
import view.ShapeObserver;
import view.StatusBarObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class DrawController {
    private static DrawController instance;
    private final List<ShapeObserver> observers;
    private final List<StatusBarObserver> statusObservers;
    private final ArrayList<Shape> shapes;
    private Shape selectedShape = null;
    private Shape shapeToDraw = null;
    private int startX, startY, endX, endY;
    private boolean isDrawing = false;
    private Color selectedColor = null;
    private ShapeTool selectedTool = ShapeTool.RECTANGLE;
    private boolean changesSaved = true;

    private DrawController() {
        shapes = new ArrayList<>();
        observers = new ArrayList<>();
        statusObservers = new ArrayList<>();
    }

    public static DrawController getInstanceOf() {
        if (instance == null) {
            instance = new DrawController();
        }
        return instance;
    }

    public void setTool(ShapeTool tool) {
        selectedTool = tool;
        notifyObserversToolChanged(tool);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void removeSelectedShape() {
        shapes.remove(selectedShape);
        selectedShape = shapes.isEmpty() ? null : shapes.get(shapes.size() - 1);
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

    public void setChangesSaved(boolean saved) {
        changesSaved = saved;
        notifyObserversFileSavedStatus(saved);
    }

    public void drawItems(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);

        if (isDrawing) {
            switch (selectedTool) {
                case RECTANGLE:
                    shapeToDraw.setPosX(x);
                    shapeToDraw.setPosY(y);
                    ((Rectangle) shapeToDraw).setWidth(width);
                    ((Rectangle) shapeToDraw).setHeight(height);
                    shapeToDraw.draw(g2d);
                    break;
                case OVAL:
                    shapeToDraw.setPosX(x);
                    shapeToDraw.setPosY(y);
                    ((Oval) shapeToDraw).setWidth(width);
                    ((Oval) shapeToDraw).setHeight(height);
                    shapeToDraw.draw(g2d);
                    break;
                case LINE:
                    shapeToDraw.setPosX(startX);
                    shapeToDraw.setPosY(startY);
                    ((Line) shapeToDraw).setEndX(endX);
                    ((Line) shapeToDraw).setEndY(endY);
                    shapeToDraw.draw(g2d);
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
            case OVAL:
                shapeToDraw = new Oval(x, y, selectedColor, 0, 0);
                break;
            case LINE:
                shapeToDraw = new Line(x, y, selectedColor, 0, 0);
                break;
        }

        this.shapeToDraw = shapeToDraw;
        selectedShape = null;
        isDrawing = true;
    }

    private int offsetX, offsetY;

    public boolean handleMouseSelection(int x, int y) {
        isDrawing = false;
        for (Shape shape : shapes) {
            if (shape.contains(x, y)) {
                setSelectedShape(shape);
                selectedColor = shape.getFill();
                offsetX = x - shape.getPosX();
                offsetY = y - shape.getPosY();
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
        setChangesSaved(false);
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
            setChangesSaved(false);
            int xPos = x - offsetX;
            int yPos = y - offsetY;
            selectedShape.move(xPos, yPos);
            notifyObserversShapeMoved();
        }
    }

    private boolean shiftPressed = false;

    public void handleKeyPressed(int keyCode) {
        if (selectedShape == null) {
            return;
        }
        setChangesSaved(false);

        int moveStep = shiftPressed ? 10 : 1;

        switch (keyCode) {
            case VK_LEFT:
                selectedShape.move(selectedShape.getPosX() - moveStep, selectedShape.getPosY());
                notifyObserversShapeMoved();
                break;
            case VK_UP:
                selectedShape.move(selectedShape.getPosX(), selectedShape.getPosY() - moveStep);
                notifyObserversShapeMoved();
                break;
            case VK_RIGHT:
                selectedShape.move(selectedShape.getPosX() + moveStep, selectedShape.getPosY());
                notifyObserversShapeMoved();
                break;
            case VK_DOWN:
                selectedShape.move(selectedShape.getPosX(), selectedShape.getPosY() + moveStep);
                notifyObserversShapeMoved();
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

    public void addStatusObserver(StatusBarObserver observer) {
        statusObservers.add(observer);
    }

    public void onFileLoaded() {
        notifyObserversShapeAdded();
        notifyObserversShapeFilled();
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

    private void notifyObserversFillChanged(Color selectedColor) {
        for (StatusBarObserver observer : statusObservers) {
            observer.fillChanged(selectedColor);
        }
    }

    private void notifyObserversToolChanged(ShapeTool tool) {
        for (StatusBarObserver observer : statusObservers) {
            observer.toolChanged(tool);
        }
    }

    private void notifyObserversFileSavedStatus(boolean saved) {
        for (StatusBarObserver observer : statusObservers) {
            observer.fileSavedStatus(saved);
        }
    }

    private void notifyObserversShapeMoved() {
        for (ShapeObserver observer : observers) {
            observer.shapeMoved();
        }
    }

    public void setColor(Color color) {
        this.selectedColor = color;
        notifyObserversFillChanged(color);
        if (selectedShape != null) {
            selectedShape.setFill(color);
            notifyObserversShapeFilled();
        }
    }

    public boolean isChangesSaved() {
        return changesSaved;
    }
}
