package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HelpFrame extends JFrame {

    HelpFrame() {
        super("Nápověda");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);


        // Create a panel to hold the help content
        JPanel contentPanel = new JPanel(new GridLayout(0, 2)); // 0 rows, 2 columns
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label1 = new JLabel("<html><b>Kreslení:</b></html>");
        JLabel label2 = new JLabel("<html>Pro nakreslení tvaru klikněte na plátno a tažením myši nakreslete tvar.</html>");

        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setHorizontalAlignment(SwingConstants.LEFT);

        contentPanel.add(label1);
        contentPanel.add(label2);

        JLabel label3 = new JLabel("<html><b>Přemístění tvarů:</b></html>");
        JLabel label4 = new JLabel("<html>Tažením myší nebo šipkami, držení SHIFT zvětší krok posunu.</html>");

        label3.setHorizontalAlignment(SwingConstants.LEFT);
        label4.setHorizontalAlignment(SwingConstants.LEFT);

        contentPanel.add(label3);
        contentPanel.add(label4);

        getContentPane().add(contentPanel);

    }
}
