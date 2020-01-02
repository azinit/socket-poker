package ru.itis.controllers;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.json.JSONObject;
import ru.itis.entities.Client;
import ru.itis.entities.Config;
import ru.itis.enums.*;
import ru.itis.interfaces.impl.DeterminedController;
import ru.itis.managers.GameManager;
import ru.itis.managers.MusicManager;
import ru.itis.managers.ScreenManager;
import ru.itis.managers.VFXManager;

import java.io.*;
import java.net.*;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.runLater;

/*
    @ project:  Socket Poker
    @ module:   GameController
    @ by:       Ilya Azin
    @ version   v.1.0
*/
public class GameController extends DeterminedController implements Initializable {

    public Client client;

    @FXML
    private StackPane stackPaneLayout;

    @FXML
    private Button myBtn;

    private GameManager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: switch music
        VFXManager.delay(1, ev -> MusicManager.play(Music.BG_POKER));
//        Thread.sleep(1)
        manager = new GameManager(this);
        client = new Client(manager);
        client.start();
        System.out.println(client.id);
    }

    @FXML
    private Label myCards;

    @FXML
    public Label infoLabel;

    @FXML
    public ImageView p1View;
    @FXML
    public ImageView p2View;
    @FXML
    public ImageView p3View;
    @FXML
    public ImageView p4View;
    @FXML
    public ImageView p5View;
    @FXML
    public ImageView p6View;
    @FXML
    public Label p1Info;
    @FXML
    public Label p2Info;
    @FXML
    public Label p3Info;
    @FXML
    public Label p4Info;
    @FXML
    public Label p5Info;
    @FXML
    public Label p6Info;


    @FXML
    public ImageView p1chipView;
    @FXML
    public ImageView p2chipView;
    @FXML
    public ImageView p3chipView;
    @FXML
    public ImageView p4chipView;
    @FXML
    public ImageView p5chipView;
    @FXML
    public ImageView p6chipView;
    @FXML
    public Label p1chipInfo;
    @FXML
    public Label p2chipInfo;
    @FXML
    public Label p3chipInfo;
    @FXML
    public Label p4chipInfo;
    @FXML
    public Label p5chipInfo;
    @FXML
    public Label p6chipInfo;

    @FXML
    public Label com1info;
    @FXML
    public Label com2info;
    @FXML
    public Label com3info;
    @FXML
    public Label com4info;
    @FXML
    public Label com5info;

    @FXML
    public ImageView com1view;
    @FXML
    public ImageView com2view;
    @FXML
    public ImageView com3view;
    @FXML
    public ImageView com4view;
    @FXML
    public ImageView com5view;

    @FXML
    public Label p1status;
    @FXML
    public Label p2status;
    @FXML
    public Label p3status;
    @FXML
    public Label p4status;
    @FXML
    public Label p5status;
    @FXML
    public Label p6status;

    @FXML
    public Label myCard1;
    @FXML
    public Label myCard2;

    @FXML
    public Label playersAmount;
    @FXML
    public Label commonBank;
    @FXML
    public Label gameStep;
    @FXML
    public Label gameRound;
    @FXML
    public Button btnCheck;

    private int count = 0;

//    public void connect() throws IOException {
//        Socket socket = new Socket("127.0.0.1", 8080);
//        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//    }

    // SWITCHING SCENES
    public void goToMainMenu(MouseEvent actionEvent) {
        client.close();
        ScreenManager.setScreen(Layout.MAIN_MENU);
    }
    public void showCommon() {
        client.showCommon();
    }

    public void takeCards() throws IOException {
//        Card[] cards = client.takeNewCards();
//        myCard1.setText(cards[0].toDetails());
//        myCard1.setTextFill(cards[0].getColor());
//
//        myCard2.setText(cards[1].toDetails());
//        myCard2.setTextFill(cards[1].getColor());
//        myCards.setText(String.format("%s\n%s", cards[0], cards[1]));
    }

    public void actionUpdate(MouseEvent mouseEvent) {
        System.out.println("::::UPDATE");
        manager.draw(client.getTotalData());
    }
    public void actionRaise(MouseEvent mouseEvent) {
        System.out.println("::RAISE");
    }

    public void actionBet(MouseEvent mouseEvent) {
        System.out.println("::BET");
        client.actionBet();
    }

    public void actionCheck(MouseEvent mouseEvent) {
        System.out.println("::CHECK");
        Button btn = (Button) mouseEvent.getSource();
        switch(btn.getText()) {
            case "Check":
                client.actionCheck();
                break;
            case "Call":
                client.actionCall();
                break;
        }
    }

    public void actionFold(MouseEvent mouseEvent) {
        System.out.println("::FOLD");
        client.actionFold();
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
}
