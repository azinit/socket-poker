package ru.itis.interfaces;

import javafx.scene.input.MouseEvent;

public interface SquadButtonDefiner {
    void squadMouseEntered(MouseEvent mouseEvent);
    void squadMouseExited(MouseEvent mouseEvent);
    void squadMousePressed(MouseEvent mouseEvent);
}
