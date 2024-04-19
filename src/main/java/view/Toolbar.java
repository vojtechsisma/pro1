package view;

import controller.ToolbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar {

    Toolbar() {
        super();
        ToolbarController controller = new ToolbarController();
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
}
