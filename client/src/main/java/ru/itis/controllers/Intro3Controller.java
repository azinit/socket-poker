package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ru.itis.enums.Layout;
import ru.itis.managers.ScreenManager;
import ru.itis.managers.VFXManager;

import java.net.URL;
import java.util.ResourceBundle;

/*
    @ project:  Socket Poker
    @ module:   Intro3Controller
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class Intro3Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VFXManager.delay(4, ev -> loadNextScene());
    }

    @FXML
    private void loadNextScene() {
        ScreenManager.setScreen(Layout.MAIN_MENU);
    }
}
