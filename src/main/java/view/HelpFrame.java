package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpFrame extends JFrame {

    final static ArrayList<String> helpContent = new ArrayList<String>(Arrays.asList(
            "Kreslení:", "Pro nakreslení tvaru klikněte na plátno a tažením myši nakreslete tvar.",
            "Přemístění tvarů:", "Tažením myší nebo šipkami, držení <i>SHIFT</i> zvětší krok posunu.",
            "Změna barvy:", "Vyberte tvar a v panelu nástrojů vyberte <i>Zvolit výplň</i>, pro odstranění výpně klikněte na <i>Zrušit výplň</i>.",
            "Odstranění tvaru:", "Vyberte tvar a klikněte na <i>Odstranit tvar</i>, nebo pomocí kláves <i>DELETE</i> nebo <i>BACKSPACE</i>.",
            "Uložení souboru:", "Klikněte na <i>Soubor</i> a vyberte <i>Uložit</i>, vyberte formát, cestu a název souboru.",
            "Načtení souboru:", "Klikněte na <i>Soubor</i> a vyberte <i>Načíst soubor</i>, vyberte soubor a potvrďte."
            ));

    HelpFrame() {
        super("Nápověda");
        setSize(500, 600);
        setMinimumSize(new Dimension(500, 600));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridLayout(0, 2)); // 0 rows, 2 columns
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        helpContent.forEach((content) -> {
            String formattedContent = helpContent.indexOf(content) % 2 == 0
                    ? "<html><b>" + content + "</b></html>"
                    : "<html>" + content + "</html>";
            JLabel label = new JLabel(formattedContent);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            contentPanel.add(label);
        });

        getContentPane().add(contentPanel);
    }
}
