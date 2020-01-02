package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import ru.itis.enums.Layout;
import ru.itis.interfaces.impl.DeterminedController;
import ru.itis.managers.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.itis.managers.VFXManager.fade;

/*
    @ project:  Socket Poker
    @ module:   CreditsController
    @ by:       Ilya Azin
    @ version   v.1.0
*/
public class CreditsController extends DeterminedController implements Initializable {

    @FXML
    private StackPane stackPaneLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // SWITCHING SCENES
    public void goToMainMenu(MouseEvent actionEvent) {
        ScreenManager.setScreen(Layout.MAIN_MENU);
    }
}
