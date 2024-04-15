package model;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle(int posX, int posY, int width, int height) {
        super(posX, posY);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Rectangle at (" + getPosX() + ", " + getPosY() + ") with width " + width + " and height " + height;
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= getPosX() && x <= getPosX() + width && y >= getPosY() && y <= getPosY() + height;
    }

    @Override
    public List<String> getTableValues() {
        List<String> values = new ArrayList<>();
        values.add("Rectangle");
        values.add(String.valueOf(getPosX()));
        values.add(String.valueOf(getPosY()));
        values.add(String.valueOf(width));
        values.add(String.valueOf(height));
        return values;
    }
}
