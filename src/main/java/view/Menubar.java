package view;

import javax.swing.*;

public class Menubar extends JMenuBar {

    // TODO: Implement menubar
    Menubar() {
        JMenu file = new JMenu("File");
        file.add(new JMenuItem("New"));
        file.add(new JMenuItem("Open"));
        add(file);
    }
}
