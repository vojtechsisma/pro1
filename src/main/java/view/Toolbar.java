package view;

import controller.ToolbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar {

    // TODO: Implement toolbar
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
