package ru.itis.bin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.itis.enums.Layout;
import ru.itis.entities.Config;
import ru.itis.managers.ScreenManager;

/**
 * .......
 * INTRO0 -> INTRO1 -> INTRO2 -> INTRO3 -> MAIN_MENU
 * .......
 * MAIN_MENU <-> GAME
 * MAIN_MENU <-> SETTINGS
 * MAIN_MENU <-> CREDITS
 * MAIN_MENU -> $DESKTOP
 * .......
 * GAME -> MAIN_MENU
 * .......
 */
public class Game extends Application {

    @Override
    public void start(Stage primaryStage){
        // Set First Screen
        ScreenManager.setScreen(Layout.INTRO_0);
        // Set First Layout
        Group root = new Group();
        root.getChildren().addAll(ScreenManager.getMainLayout());
        // Set First Stage
        primaryStage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, Color.BLACK));
        primaryStage.setTitle("Socket Poker v.0.1");
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}


class Main {
    public static void main(String[] args) {
        Application.launch(Game.class);
    }
}