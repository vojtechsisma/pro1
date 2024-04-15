package view;

import controller.DrawController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel {

    private final DrawController controller = DrawController.getInstanceOf();

    public DrawingPanel() {
        super();
        setBackground(Color.BLUE);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                notifyControllerMousePressed(e);
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        controller.drawItems(g);
    }

    private void notifyControllerMousePressed(MouseEvent e) {
        controller.handleMousePressed(e.getX(), e.getY());

    }

    private void notifyControllerMouseReleased(MouseEvent e) {
        controller.handleMouseReleased(e.getX(), e.getY());
        repaint();
    }

    private void notifyControllerMouseDragged(MouseEvent e) {
        controller.handleMouseDragged(e.getX(), e.getY());
        repaint();
    }
}
