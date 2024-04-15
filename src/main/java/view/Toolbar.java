package view;

import controller.ToolbarController;

import javax.swing.*;
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
    }
}
