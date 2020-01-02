package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import ru.itis.enums.Background;
import ru.itis.enums.Layout;
import ru.itis.interfaces.impl.DeterminedController;
import ru.itis.managers.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.itis.managers.VFXManager.fade;

/*
    @ project:  Socket Poker
    @ module:   Intro1Controller
    @ by:       Ilya Azin
    @ version   v.1.0
*/
public class SettingsController extends DeterminedController implements Initializable {

    @FXML
    private StackPane stackPaneLayout;

    @FXML
    private ImageView imgBack;

    private Button lastSelected;
    private String lastSelectedText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // SWITCHING SCENES
    public void goToMainMenu(MouseEvent actionEvent) {
        ScreenManager.setScreen(Layout.MAIN_MENU);
    }

    // SWITCHING TABS
    public void switchOnGame(ActionEvent actionEvent) {
        switchOn(actionEvent, Background.SETTINGS_GAME);
    }

    public void switchOnGraphics(ActionEvent actionEvent) {
        switchOn(actionEvent, Background.SETTINGS_GRAPHICS);
    }

    public void switchOnAudio(ActionEvent actionEvent) {
        switchOn(actionEvent, Background.SETTINGS_AUDIO);
    }

    public void switchOnLanguage(ActionEvent actionEvent) {
        switchOn(actionEvent, Background.SETTINGS_LANGUAGE);
    }

    private void switchOn(ActionEvent actionEvent, Background img) {
        String styleSelected = "-fx-text-fill: white;";
        String styleBase = "-fx-text-fill: #e5d1a9;";

//        Button temp = lastSelected;
        Button selected = ((Button) actionEvent.getSource());
        if (lastSelected != null) {
            lastSelected.setStyle(styleBase);
        }
        lastSelected = selected;
        selected.setStyle(styleSelected);

        imgBack.setImage(img.getImage());

    }
}
