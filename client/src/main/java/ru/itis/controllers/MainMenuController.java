package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import ru.itis.enums.Layout;
import ru.itis.enums.MainMenuTheme;
import ru.itis.interfaces.impl.DeterminedController;
import ru.itis.entities.Config;
import ru.itis.managers.MusicManager;
import ru.itis.managers.ScreenManager;
import ru.itis.managers.VFXManager;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/*
    @ project:  Socket Poker
    @ module:   MainMenuController
    @ created:  12/11/2018
    @ by:       Ilya Azin
    @ version   v.1.0
*/

public class MainMenuController extends DeterminedController implements Initializable {

    @FXML
    private StackPane stackPaneLayout;

    @FXML
    private ImageView ibtnMusic;

    @FXML
    private ImageView ibtnMusicLight;

    @FXML
    private ImageView imgBack;

    @FXML
    private Button btnThemeSwitcher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VFXManager.delay(1, ev -> setUserTheme());
    }

    public void goToGame(ActionEvent actionEvent) {
        ScreenManager.setScreen(Layout.GAME);
    }

    // SWITCHING SCENES
    public void goToMapChoicer(ActionEvent actionEvent) {
        ScreenManager.setScreen(Layout.MAP_CHOICER);
    }

    public void goToSettings(ActionEvent actionEvent) {
        ScreenManager.setScreen(Layout.SETTINGS);
    }

    public void goToCredits(ActionEvent actionEvent) {
        ScreenManager.setScreen(Layout.CREDITS);
    }

    public void goToDesktop(ActionEvent actionEvent) {
        // TODO: ~modal WIndow
        System.out.println("Come Back!");
        System.exit(0);
    }

    // SWITCHING ON/OFF MUSIC
    public void switchMusicState(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        Image musicOff = new Image("/buttons/h_btnMusicOff.png");
        Image musicOn = new Image("/buttons/h_btnMusicOn.png");

        Config.wishMusic ^= true;
        if (Config.wishMusic) {
            MusicManager.play();
            imageView.setImage(musicOn);
            System.out.println("\uD83D\uDD6C: " + "on");
        } else {
            MusicManager.pause();
            imageView.setImage(musicOff);
            System.out.println("\uD83D\uDD6C: " + "off");
        }
    }

    // SWITCHING THEMES
    @FXML
    private void switchTheme(ActionEvent actionEvent) {
        Config.themeID = (Config.themeID < MainMenuTheme.size() - 1) ? Config.themeID + 1 : 0;
        setTheme(MainMenuTheme.values()[Config.themeID]);
    }

    private void setUserTheme() {
        if (Config.themeID == -1) {
            Config.themeID = new Random().nextInt(MainMenuTheme.size());
        }

        setTheme(Config.getTheme());
    }

    private void setTheme(MainMenuTheme theme) {
        imgBack.setImage(theme.getImage());
        if (Config.wishMusic) {
            MusicManager.play(theme.getMusic());
        }

        switch (theme) {
            case VANILLA:
                btnThemeSwitcher.setStyle("-fx-text-fill: #65b5d1;");
                break;
            case DARK:
                btnThemeSwitcher.setStyle("-fx-text-fill: #c33a5d;");
                break;
            case MYSTIC:
                btnThemeSwitcher.setStyle("-fx-text-fill: #927dca;");
                break;
            case WINTER:
                btnThemeSwitcher.setStyle("-fx-text-fill: white");
                break;
            default:
                btnThemeSwitcher.setStyle("-fx-text-fill: #6c5934;");
                break;

        }
    }
/*    // ICOBUTTON FX
    public void fadeOutFrame(MouseEvent mouseEvent) {
        fade(ibtnMusicLight, 1.0, 0.0, 0.9);
    }

    public void fadeInFrame(MouseEvent mouseEvent) {
        fade(ibtnMusicLight, 0.0, 1.0, 0.5);
    }*/
}
