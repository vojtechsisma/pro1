package view;

import controller.DrawController;
import model.ShapeTool;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StatusBar extends JPanel implements StatusBarObserver {
    static final String toolText = "Nástroj: ";
    JLabel toolLabel = new JLabel(toolText + getToolName(ShapeTool.RECTANGLE));
    static final String fillText = "Výplň: ";
    JLabel fillLabel = new JLabel(fillText + "N/A");
    static final String fileSaveText = "Uloženo: ";
    JLabel fileSaveLabel = new JLabel(fileSaveText + "ano");


    StatusBar() {
        super();
        DrawController drawController = DrawController.getInstanceOf();
        drawController.addStatusObserver(this);

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        toolLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(toolLabel);

        add(Box.createRigidArea(new Dimension(20, 0)));

        fillLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(fillLabel);

        add(Box.createRigidArea(new Dimension(20, 0)));

        fileSaveLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(fileSaveLabel);
    }

    @Override
    public void fillChanged(Color color) {
        fillLabel.setText(fillText + (color == null ? "N/A" : color.toString()));
    }

    @Override
    public void fileSavedStatus(boolean saved) {
        fileSaveLabel.setText(fileSaveText + (saved ? "ano" : "ne"));
    }

    @Override
    public void toolChanged(ShapeTool tool) {
        toolLabel.setText(toolText + getToolName(tool));
    }

    private String getToolName(ShapeTool tool) {
        return switch (tool) {
            case RECTANGLE -> "Obdélník";
            case OVAL -> "Ovál";
            case LINE -> "Úsečka";
            default -> "N/A";
        };
    }
}
