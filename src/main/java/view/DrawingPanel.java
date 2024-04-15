package view;

import controller.DrawController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel implements ShapeObserver {

    private final DrawController controller = DrawController.getInstanceOf();

    public DrawingPanel() {
        super();
        setBackground(Color.BLUE);
        controller.addObserver(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                notifyControllerMousePressed(e);
                requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                notifyControllerMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                notifyControllerMouseDragged(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPressed(e.getKeyCode());
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                controller.handleKeyReleased(e.getKeyCode());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        controller.drawItems(g);
    }

    private void notifyControllerMousePressed(MouseEvent e) {
        if (controller.handleMouseSelection(e.getX(), e.getY())) {
            repaint();
            return;
        }

        controller.handleMousePressed(e.getX(), e.getY());
    }

    private void notifyControllerMouseReleased(MouseEvent e) {
        controller.handleMouseReleased(e.getX(), e.getY());
    }

    private void notifyControllerMouseDragged(MouseEvent e) {
        controller.handleMouseDragged(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void shapeAdded() {
        repaint();
    }

    @Override
    public void shapeRemoved() {
        repaint();
    }
}
