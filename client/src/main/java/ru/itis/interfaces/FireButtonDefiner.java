package ru.itis.interfaces;

import javafx.scene.input.MouseEvent;

public interface FireButtonDefiner {
    void fireMouseEntered(MouseEvent mouseEvent);
    void fireMouseExited(MouseEvent mouseEvent);
    void fireMousePressed(MouseEvent mouseEvent);
}
