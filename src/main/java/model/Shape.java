package model;

import controller.DrawController;

import java.util.List;

public abstract class Shape {
    private int posX;
    private int posY;

    public Shape(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isSelected() {
        return DrawController.getInstanceOf().selectedShape == this;
    }

    abstract public boolean contains(int x, int y);

    abstract public List<String> getTableValues();
}
