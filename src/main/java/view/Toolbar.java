package view;

import controller.ToolbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class Toolbar extends JToolBar {

    Toolbar() {
        super();
        ToolbarController controller = new ToolbarController();

        JButton rectangle = new JButton("", getButtonIcon("rect"));
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setRectangleTool();
            }
        });
        add(rectangle);

        add(Box.createRigidArea(new Dimension(5, 0)));

        JButton oval = new JButton("", getButtonIcon("oval"));
        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setOvalTool();
            }
        });
        add(oval);

        add(Box.createRigidArea(new Dimension(5, 0)));

        JButton line = new JButton("", getButtonIcon("line"));
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLineTool();
            }
        });
        add(line);

        add(Box.createRigidArea(new Dimension(5, 0)));

        JButton removeShape = new JButton("Odstranit tvar");
        removeShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeSelectedShape();
            }
        });
        add(removeShape);

        add(Box.createRigidArea(new Dimension(5, 0)));

        JButton selectFill = new JButton("Zvolit výplň");
        selectFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(selectFill, "Select a Color", Color.BLACK);
                if (selectedColor != null) {
                    controller.setColor(selectedColor);
                }
            }
        });
        add(selectFill);

        add(Box.createRigidArea(new Dimension(5, 0)));


        JButton removeFill = new JButton("Zrušit výplň");
        removeFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setColor(null);
            }
        });
        add(removeFill);
    }

    private ImageIcon getButtonIcon(String icon) {
        URL imageUrl = Objects.requireNonNull(Toolbar.class.getResource("/icons/" + icon + ".png"));
        ImageIcon originalIcon = new ImageIcon(imageUrl);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
