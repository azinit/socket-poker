package ru.itis.entities;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.itis.enums.Card;
import ru.itis.managers.GameManager;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private final GameManager manager;
    // TODO: add view
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket = null;
    public Card[] myCards;
    public Integer id;
    public Integer money;
    public Boolean isPlaying;
    public Boolean isActive;
    public Boolean isAccepted;
//    public Boolean areCardsGiven;

    public Client(GameManager manager) {
        this.manager = manager;
    }

    public void update() {
        JSONObject data = getData();
        id = data.getInt("id");
        money = data.getInt("money");
        isPlaying = data.getBoolean("playing");
        isAccepted = data.getBoolean("accepted");
        isActive = data.getBoolean("active");
//        areCardsGiven = data.getBoolean("areCardsGiven");
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 8080);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            myCards = new Card[2];
            myCards = takeCards();
            this.update();
            manager.setClient(this);

            while (true) {
                try {
                    // TODO: getTotalData -> from Game not by request?
                    // >>> https://coderanch.com/t/696710/java/Communication-client-server-javafx
                    // https://stackoverflow.com/questions/47150884/label-not-being-automatically-updated-with-thread-in-javafx
                    // https://www.youtube.com/watch?v=VVUuo9VO2II
                    // https://stackoverflow.com/questions/39212959/how-to-multithread-with-javafx
                    // https://www.daniweb.com/programming/software-development/threads/520369/javafx-server-client-program
                    // https://coderanch.com/t/678109/java/create-task-JavaFX-application-runnable
                    manager.draw(getTotalData());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Card getCard() {
        // TODO:        org.json.JSONException: JSONObject["card"] not found.
        JSONObject request = new JSONObject();
        request.put("action", "get_random_card");
        JSONObject response = compute(request);
        assert response != null;
        try {
            String card = response.getJSONObject("data").get("card").toString();
//        System.out.println(card);
            return Card.deserialize(card);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getData() {
        JSONObject request = new JSONObject();
        request.put("action", "get_data");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getTotalData() {
        JSONObject request = new JSONObject();
        request.put("action", "get_total_data");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject compute(JSONObject request) {
        writer.println(request.toString());
        writer.flush();

        try {
            JSONObject response = new JSONObject(reader.readLine());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getServerRequest() {
        try {
            JSONObject request = new JSONObject(reader.readLine());
            return request;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void terminate() {
        JSONObject request = new JSONObject();
        request.put("action", "exit");
        compute(request);
    }

    public void close() {
        try {
            terminate();
            socket.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @action get_my_cards
     */
    public Card[] takeNewCards() {
        // get Cards, get from Server if preflop
        JSONObject request = new JSONObject();
        request.put("action", "take_new_cards");
        JSONObject response = compute(request);
        assert response != null;
        JSONArray myCardsJSON = response.getJSONObject("data").getJSONArray("cards");
        myCards[0] = Card.deserialize(myCardsJSON.get(0).toString());
        myCards[1] = Card.deserialize(myCardsJSON.get(1).toString());
//        myCards[0] = getCard();
//        myCards[1] = getCard();
        System.out.printf("My cards: %s, %s\n", myCards[0], myCards[1]);
        return myCards;
    }

    public Card[] takeCards() {
        // get Cards, get from Server if preflop
        JSONObject request = new JSONObject();
        request.put("action", "get_my_cards");
        JSONObject response = compute(request);
        assert response != null;
        JSONArray myCardsJSON = response.getJSONObject("data").getJSONArray("cards");
        myCards[0] = Card.deserialize(myCardsJSON.get(0).toString());
        myCards[1] = Card.deserialize(myCardsJSON.get(1).toString());
//        myCards[0] = getCard();
//        myCards[1] = getCard();
        System.out.printf("My cards: %s, %s\n", myCards[0], myCards[1]);
        return myCards;
    }

    /**
     * @action send_action
     */
    public void dispatch(JSONObject request) {
        System.out.println(request);
    }

    public JSONObject actionBet() {
        JSONObject request = new JSONObject();
        request.put("action", "action_bet");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject actionCheck() {
        JSONObject request = new JSONObject();
        request.put("action", "action_check");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject actionCall() {
        JSONObject request = new JSONObject();
        request.put("action", "action_call");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject actionFold() {
        JSONObject request = new JSONObject();
        request.put("action", "action_fold");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject showCommon() {
        JSONObject request = new JSONObject();
        request.put("action", "show_common");
        JSONObject response = compute(request);
        assert response != null;
        try {
            return response.getJSONObject("data");
        } catch (Exception e) {
            return null;
        }
    }
}

//class Main {
//    public static void main(String[] args) {
//        Client client = new Client();
//        Card card = client.getCard();
//        System.out.println(card);
//        client.close();
//    }
//}

