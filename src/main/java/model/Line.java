package model;

import controller.FileController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line extends Shape {
    private int endX;
    private int endY;
    private ShapeTool shape = ShapeTool.LINE;

    public Line() {
        super(0, 0, null);
    }

    public Line(int posX, int posY, Color fill, int endX, int endY) {
        super(posX, posY, fill);
        this.endX = endX;
        this.endY = endY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    @Override
    public String toString() {
        return "Line at (" + getPosX() + ", " + getPosY() + ") (" + endX + ", " + endY + ") and color " + getFill();
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= getPosX() && x <= getPosX() + endX && y >= getPosY() && y <= getPosY() + endY;
    }

    @Override
    public java.util.List<String> getTableValues() {
        List<String> values = new ArrayList<>();
        values.add("Úsečka");
        values.add(String.valueOf(getPosX()));
        values.add(String.valueOf(getPosY()));
        values.add(String.valueOf(getEndX()));
        values.add(String.valueOf(getEndY()));
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
            g.drawLine(this.getPosX(), this.getPosY(), this.getEndX(), this.getEndY());
            return;
        }
        g.setColor(Color.BLACK);
        g.drawLine(this.getPosX(), this.getPosY(), this.getEndX(), this.getEndY());
    }

    @Override
    public void drawSelection(Graphics g) {
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{6}, 0);
        ((Graphics2D) g).setStroke(dashed);
        g.setColor(Color.RED);
        g.drawLine(this.getPosX() - 2, this.getPosY() - 2, this.getEndX() - 2, this.getEndY() - 2);
        ((Graphics2D) g).setStroke(new BasicStroke());
    }

    @Override
    public void move(int newStartX, int newStartY) {
        int deltaX = newStartX - getPosX();
        int deltaY = newStartY - getPosY();
        setPosX(newStartX);
        setPosY(newStartY);
        endX += deltaX;
        endY += deltaY;
    }

    @Override
    public String toCsv() {
        String[] values = {
                shape.toString(),
                String.valueOf(getPosX()),
                String.valueOf(getPosY()),
                String.valueOf(getEndX()),
                String.valueOf(getEndY()),
                getFill() != null ? String.valueOf(getFill().getRGB()) : "null"
        };

        return String.join(FileController.CSV_DELIMITER, values);
    }

    public Line fromCsv(String csv) {
        String[] values = csv.split(FileController.CSV_DELIMITER);
        Line line = new Line();
        line.setPosX(Integer.parseInt(values[1]));
        line.setPosY(Integer.parseInt(values[2]));
        line.setEndX(Integer.parseInt(values[3]));
        line.setEndY(Integer.parseInt(values[4]));
        line.setFill(!Objects.equals(values[5], "null") ? new Color(Integer.parseInt(values[5])) : null);
        return line;
    }
}
