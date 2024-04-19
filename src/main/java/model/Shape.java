package model;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import controller.DrawController;

import java.awt.*;
import java.util.List;

public abstract class Shape {
    private int posX;
    private int posY;

    @JsonAdapter(ColorAdapter.class)
    private Color fill;

    public Shape(int posX, int posY, Color fill) {
        this.posX = posX;
        this.posY = posY;
        this.fill = fill;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isSelected() {
        return DrawController.getInstanceOf().getSelectedShape() == this;
    }

    abstract public boolean contains(int x, int y);

    abstract public List<String> getTableValues();

    abstract public void draw(Graphics g);

    abstract public void drawSelection(Graphics g);

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Color getFill() {
        return fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public void move(int x, int y) {
        posX = x;
        posY = y;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
