package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ru.itis.managers.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

/*
    @ project:  Socket Poker
    @ module:   TestController
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class TestController implements Initializable {
    @FXML
    private AnchorPane myLayout;
    @FXML
    private Button myBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myLayout.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            System.out.println("YOU HAVE CLICKED: ? !!!");
            if (event.getCode() == KeyCode.D) {
                System.out.println("YOU HAVE CLICKED: D!!!");
            }
        });

//        myBtn.setOnAction(event -> System.out.println("MY BUTTON!!!"));
    }
}
