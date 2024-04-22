package model;

import controller.FileController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Oval extends Shape {
    private int width;
    private int height;
    private ShapeTool shape = ShapeTool.OVAL;

    public Oval() {
        super(0, 0, null);
    }

    public Oval(int posX, int posY, Color fill, int width, int height) {
        super(posX, posY, fill);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Oval at (" + getPosX() + ", " + getPosY() + ") with width " + getWidth() + " and height " + getHeight() + " and fill " + getFill();
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= getPosX() && x <= getPosX() + width && y >= getPosY() && y <= getPosY() + height;
    }

    @Override
    public java.util.List<String> getTableValues() {
        List<String> values = new ArrayList<>();
        values.add("Ovál");
        values.add(String.valueOf(getPosX()));
        values.add(String.valueOf(getPosY()));
        values.add(String.valueOf(width));
        values.add(String.valueOf(height));
        values.add(getFill() != null ? String.format("(%s, %s, %s)", getFill().getRed(), getFill().getGreen(), getFill().getBlue()) : "null");
        return values;
    }

    @Override
    public void draw(Graphics g) {
        if (this.isSelected()) {
            drawSelection(g);
        }

        if (this.getFill() != null) {
            g.setColor(this.getFill());
            g.fillOval(this.getPosX(), this.getPosY(), width, height);
            return;
        }
        g.setColor(Color.BLACK);
        g.drawOval(this.getPosX(), this.getPosY(), width, height);
    }

    @Override
    public void drawSelection(Graphics g) {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{6}, 0);
        ((Graphics2D) g).setStroke(dashed);
        g.setColor(Color.RED);
        g.drawOval(this.getPosX() - 2, this.getPosY() - 2, width + 4, height + 4);
        ((Graphics2D) g).setStroke(new BasicStroke());
    }

    @Override
    public String toCsv() {
        String[] values = {
                shape.toString(),
                String.valueOf(getPosX()),
                String.valueOf(getPosY()),
                String.valueOf(width),
                String.valueOf(height),
                getFill() != null ? String.valueOf(getFill().getRGB()) : "null"
        };

        return String.join(FileController.CSV_DELIMITER, values);
    }

    public Oval fromCsv(String csv) {
        String[] values = csv.split(FileController.CSV_DELIMITER);
        Oval oval = new Oval();
        oval.setPosX(Integer.parseInt(values[1]));
        oval.setPosY(Integer.parseInt(values[2]));
        oval.setWidth(Integer.parseInt(values[3]));
        oval.setHeight(Integer.parseInt(values[4]));
        oval.setFill(!Objects.equals(values[5], "null") ? new Color(Integer.parseInt(values[5])) : null);
        return oval;
    }
}
