package ru.itis.managers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.itis.controllers.GameController;
import ru.itis.entities.Client;
import ru.itis.enums.Card;
import ru.itis.enums.Component;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static javafx.application.Platform.runLater;

// Logic/Renderer...
public class GameManager {
    private GameController controller;
    private ArrayList<ImageView> playersViews;
    private ArrayList<Label> playersLabels;
    private ArrayList<ImageView> chipsViews;
    private ArrayList<Label> chipsLabels;
    private ArrayList<ImageView> comViews;
    private ArrayList<Label> comLabels;
    private ArrayList<Label> playersStatuses;
    private ArrayList<Label> myCardsDetails;
    private Client client;

    public GameManager(GameController controller) {
        this.controller = controller;
        this.client = controller.client;
        this.playersViews = new ArrayList<>();
        this.playersLabels = new ArrayList<>();
        this.chipsViews = new ArrayList<>();
        this.chipsLabels = new ArrayList<>();
        this.comViews = new ArrayList<>();
        this.comLabels = new ArrayList<>();
        this.playersStatuses = new ArrayList<>();
        this.myCardsDetails = new ArrayList<>();

        playersViews.add(controller.p1View);
        playersViews.add(controller.p2View);
        playersViews.add(controller.p3View);
        playersViews.add(controller.p4View);
        playersViews.add(controller.p5View);
        playersViews.add(controller.p6View);

        playersLabels.add(controller.p1Info);
        playersLabels.add(controller.p2Info);
        playersLabels.add(controller.p3Info);
        playersLabels.add(controller.p4Info);
        playersLabels.add(controller.p5Info);
        playersLabels.add(controller.p6Info);

        chipsViews.add(controller.p1chipView);
        chipsViews.add(controller.p2chipView);
        chipsViews.add(controller.p3chipView);
        chipsViews.add(controller.p4chipView);
        chipsViews.add(controller.p5chipView);
        chipsViews.add(controller.p6chipView);

        chipsLabels.add(controller.p1chipInfo);
        chipsLabels.add(controller.p2chipInfo);
        chipsLabels.add(controller.p3chipInfo);
        chipsLabels.add(controller.p4chipInfo);
        chipsLabels.add(controller.p5chipInfo);
        chipsLabels.add(controller.p6chipInfo);

        comViews.add(controller.com1view);
        comViews.add(controller.com2view);
        comViews.add(controller.com3view);
        comViews.add(controller.com4view);
        comViews.add(controller.com5view);

        comLabels.add(controller.com1info);
        comLabels.add(controller.com2info);
        comLabels.add(controller.com3info);
        comLabels.add(controller.com4info);
        comLabels.add(controller.com5info);

        playersStatuses.add(controller.p1status);
        playersStatuses.add(controller.p2status);
        playersStatuses.add(controller.p3status);
        playersStatuses.add(controller.p4status);
        playersStatuses.add(controller.p5status);
        playersStatuses.add(controller.p6status);

        myCardsDetails.add(controller.myCard1);
        myCardsDetails.add(controller.myCard2);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void draw(JSONObject data) {
        String step = data.getString("step");
        Integer round = data.getInt("round");
        // draw clients, chips
        JSONArray clients = data.getJSONArray("clients");
        clients.forEach((client) -> {
            JSONObject clientData = (JSONObject) client;
            drawPlayer(clientData, step);
            drawChip(clientData);
        });
        // draw common cards
        JSONArray common = data.getJSONArray("common");
        common.forEach(card -> {
            JSONObject cardTotal = (JSONObject) card;
            drawCard(cardTotal, step);
        });
        // draw labels
        runLater(() -> controller.playersAmount.setText(String.format("Игроков: %s/6", clients.length())));
        runLater(() -> controller.commonBank.setText(String.format("Общий банк: %s", data.getInt("totalSum"))));
        runLater(() -> controller.gameStep.setText(String.format("Этап: %s", step)));
        runLater(() -> controller.gameRound.setText(String.format("Раунд: %s", round)));
    }

    private void drawCard(JSONObject cardTotal, String step) {
        int index = cardTotal.getInt("index");
        String card = cardTotal.getString("card");

        ImageView view = comViews.get(index);
        Label label = comLabels.get(index);
        if (!card.equals("")) {
            view.setImage(Component.COMMON_CARDS.getImage());
            Card card1 = Card.deserialize(card);
            runLater(() -> label.setText(card1.toDetails()));
//            runLater(() -> label.setTextFill(card1.getColor()));
        } else {
            view.setImage(Component.NONE_COMMON_CARDS.getImage());
            runLater(() -> label.setText(""));
        }
    }

    private void drawChip(JSONObject data) {
        int id = data.getInt("id");
        int bet = data.getInt("bet");

        ImageView view = chipsViews.get(id - 1);
        Label label = chipsLabels.get(id - 1);

        if (bet > 0) {
            view.setImage(Component.CHIPS.getImage());
            runLater(() -> { label.setText(String.valueOf(bet)); } );
        } else {
            view.setImage(Component.NONE_CHIPS.getImage());
            runLater(() -> { label.setText(""); } );
        }
    }
    private void drawPlayer(JSONObject data, String step) {
        int id = data.getInt("id");
        int money = data.getInt("money");
        boolean isCalled = data.getBoolean("called");
        boolean isActive = data.getBoolean("active");
        boolean isPlaying = data.getBoolean("playing");
        boolean isWinner = data.getBoolean("winner");
        boolean isPlayer = id == client.id;
        String status = data.getString("status");

        boolean isShownDown = step.equals("SHOWN_DOWN");
        Card[] myCards = new Card[2];
        int i = 0;
        data.getJSONArray("myCards").forEach(item -> {
            JSONObject itemData = (JSONObject) item;
            int index = itemData.getInt("index");
            String card = itemData.getString("card");
            myCards[index] = Card.deserialize(card);
        });
        ImageView view = playersViews.get(id - 1);
        Label label = playersLabels.get(id - 1);
        Label statusLabel = playersStatuses.get(id - 1);

        // renderView
        if (isPlaying) {
            if (isPlayer) {
                if (isActive) {
                    if (isCalled) {
                        runLater(() -> controller.btnCheck.setText("Check"));
                    } else {
                        runLater(() -> controller.btnCheck.setText("Call"));
                    }
                    view.setImage(Component.MY_ACTIVE_CARDS.getImage());
                } else {
                    view.setImage(Component.MY_CARDS.getImage());
                }
            } else {
                if (isActive) {
                    view.setImage(Component.ACTIVE_CARDS.getImage());
                } else {
                    view.setImage(Component.CARDS.getImage());
                }
            }
        } else {
            if (isPlayer) {
                view.setImage(Component.MY_NONE_CARDS.getImage());
            } else {
                view.setImage(Component.NONE_CARDS.getImage());
            }
        }

        // renderInfo
        StringBuilder template = new StringBuilder();
        if (isShownDown) {
            template.append(myCards[0]).append(" ").append(myCards[1]).append("\n");
        }
        if (isShownDown && isWinner) {
            if (isPlayer) {
                view.setImage(Component.MY_WIN_CARDS.getImage());
            } else {
                view.setImage(Component.WIN_CARDS.getImage());
            }
        }

        if (isPlayer) {
            for (int j = 0; j < 2; j++) {
                Card card = myCards[j];
                Label myCardDetail = myCardsDetails.get(j);
                runLater(() -> myCardDetail.setText(card.toDetails()));
                runLater(() -> myCardDetail.setTextFill(card.getColor()));

            }
        }

        template.append("⛁ ").append(money);
        runLater(() -> label.setText(template.toString()));
        runLater(() -> statusLabel.setText(status));
    }
}
