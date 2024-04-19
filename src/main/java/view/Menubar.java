package view;

import controller.MenuController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menubar extends JMenuBar {
    JPanel parent;

    Menubar(JPanel parent) {
        super();
        this.parent = parent;

        MenuController controller = new MenuController();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));

        JMenu file = new JMenu("Soubor");
        JMenu save = new JMenu("Uložit");
        JMenuItem saveJson = new JMenuItem("JSON");
        saveJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileChooser.setSelectedFile(new File("shapes.json"));
                    int status = fileChooser.showSaveDialog(null);
                    if (status == JFileChooser.APPROVE_OPTION) {
                        controller.saveJson(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Chyba při ukládání JSON souboru", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        save.add(saveJson);

        JMenuItem saveImage = new JMenuItem("Obrázek");
        saveImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileChooser.setSelectedFile(new File("image.png"));
                    int status = fileChooser.showSaveDialog(null);
                    if (status == JFileChooser.APPROVE_OPTION) {
                        controller.saveImage(parent, fileChooser.getSelectedFile().getAbsolutePath());
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Chyba při ukládání souboru", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        save.add(saveImage);
        file.add(save);

        JMenuItem loadJson = new JMenuItem("Načíst JSON");
        loadJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
                    int status = fileChooser.showOpenDialog(null);
                    if (status == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        controller.loadJson(file);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Chyba při načítání JSON souboru", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
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

        JMenuItem help = new JMenuItem("Nápověda");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    HelpFrame helpFrame = new HelpFrame();
                    helpFrame.setVisible(true);
                });
            }
        });
        add(help);
    }
}
