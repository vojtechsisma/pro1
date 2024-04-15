package controller;

import model.Rectangle;
import model.Shape;

import java.awt.*;
import java.util.ArrayList;

public class DrawController {

    private static DrawController instance;
    public ArrayList<Shape> shapes = new ArrayList<Shape>();
    public Shape selectedShape = null;

    public static DrawController getInstanceOf() {
        if (instance == null) {
            instance = new DrawController();
        }
        return instance;
    }

    public void drawItems(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

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
}
