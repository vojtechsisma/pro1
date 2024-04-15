package view;

import controller.DrawController;
import model.Rectangle;
import model.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final List<ShapeObserver> observers;

    private int startX, startY, endX, endY;
    private boolean isDrawing = false;

     public DrawingPanel() {
        super();
        setBackground(Color.BLUE);
        observers = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseRelease(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isDrawing) {
            drawShapePreview(g);
        }

        DrawController.getInstanceOf().drawItems(g);
    }

    public void drawShapePreview(Graphics g) {
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        g.drawRect(x, y, width, height);
    }

    public void addObserver(ShapeObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (ShapeObserver observer : observers) {
            observer.shapeAdded();
        }
    }

    public void handleMousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();

        for (Shape shape : DrawController.getInstanceOf().shapes) {
            if (shape.contains(startX, startY)) {
                DrawController.getInstanceOf().selectedShape = shape;
                repaint();
                return;
            }
        }

        isDrawing = true;
    }

    public void handleMouseRelease(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        isDrawing = false;

        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        if (width == 0 || height == 0) {
            return;
        }

        Rectangle rectangle = new Rectangle(Math.min(startX, endX), Math.min(startY, endY), width, height);
        DrawController.getInstanceOf().shapes.add(rectangle);

        notifyObservers();
        repaint();
    }

    public void handleMouseDragged(MouseEvent e) {
        if (isDrawing) {
            endX = e.getX();
            endY = e.getY();
            repaint();
        }
    }

}
