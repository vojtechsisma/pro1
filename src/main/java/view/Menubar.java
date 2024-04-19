package view;

import controller.MenuController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menubar extends JMenuBar {

    // TODO: Implement menubar
    Menubar() {
        MenuController controller = new MenuController();

        JMenu file = new JMenu("Soubor");
        JMenuItem saveJson = new JMenuItem("Uložit JSON");
        saveJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveJson();
            }
        });
        file.add(saveJson);

        JMenuItem loadJson = new JMenuItem("Načíst JSON");
        loadJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadJson();
            }
        });
        file.add(loadJson);
        add(file);

        JMenu shapes = new JMenu("Tvary");

        JMenuItem rectangle = new JMenuItem("Obdelník");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setRectangleTool();
            }
        });
        shapes.add(rectangle);

        JMenuItem oval = new JMenuItem("Ovál");
        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setOvalTool();
            }
        });
        shapes.add(oval);

        JMenuItem line = new JMenuItem("Úsečka");
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLineTool();
            }
        });
        shapes.add(line);

        add(shapes);
    }
}
