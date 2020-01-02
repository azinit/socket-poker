package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @ module:   MapChoicerController
    @ by:       Ilya Azin
    @ version   v.1.0
*/
public class MapChoicerController extends DeterminedController implements Initializable {

    public ImageView ibtnReturn;
    @FXML
    private StackPane stackPaneLayout;

    @FXML
    private ImageView ibtnGo;

    @FXML
    private ImageView ibtnGoFrame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPaneLayout.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.D) {
                System.out.println("TAAAAAKE OOOON MEEE");
            }
        });
    }

    // SWITCHING SCENES
    public void goToMainMenu(MouseEvent actionEvent) {
        ScreenManager.setScreen(Layout.MAIN_MENU);
    }

    public void goToGame(MouseEvent actionEvent) {
        ScreenManager.setScreen(Layout.GAME);
    }

    // BUTTONS

    public void fadeOutFrame(MouseEvent mouseEvent) {
        fade(ibtnGoFrame, 1.0, 0.2, 1);
    }

    public void fadeInFrame(MouseEvent mouseEvent) {
        fade(ibtnGoFrame, 0.2, 1.0, 1);
    }
}
