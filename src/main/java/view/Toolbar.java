package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar {

    // TODO: Implement toolbar
    Toolbar() {
        super();
        JButton addShapeButton = new JButton("Add Shape");
        addShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Shape button clicked");
            }
        });
        add(addShapeButton);
    }
}
