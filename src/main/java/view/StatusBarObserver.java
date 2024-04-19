package view;

import model.ShapeTool;

import java.awt.*;

public interface StatusBarObserver {
    void fillChanged(Color color);
    void fileSavedStatus(boolean saved);
    void toolChanged(ShapeTool tool);
}
