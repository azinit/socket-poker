package ru.itis.enums;

import javafx.scene.image.Image;

import java.nio.file.Paths;

public enum Component {
    CARDS("cards.png"),
    ACTIVE_CARDS("h_cards.png"),
    NONE_CARDS("none_cards.png"),
    WIN_CARDS("win_cards.png"),
    MY_CARDS("my_cards.png"),
    MY_ACTIVE_CARDS("my_h_cards.png"),
    MY_NONE_CARDS("my_none_cards.png"),
    MY_WIN_CARDS("my_win_cards.png"),
    CHIPS("chips.png"),
    NONE_CHIPS("none_chips.png"),
    COMMON_CARDS("com_card.png"),
    NONE_COMMON_CARDS("none_chips.png");



    private String imageFile;

    Component(String imageFile) {
        this.imageFile = imageFile;
    }

    public Image getImage() {
        return new Image(Paths.get("src/main/resources/components/" + imageFile).toUri().toString());
//        return new Image(Paths.get("/buttons/" + imageFile).toUri().toString());
    }

    @Override
    public String toString() {
        return imageFile;
    }
}