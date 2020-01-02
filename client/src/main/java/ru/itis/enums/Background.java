package ru.itis.enums;

import javafx.scene.image.Image;

import java.nio.file.Paths;

public enum Background {
    MAIN_MENU_VANILLA("main_menu_vanilla.jpg"),
    MAIN_MENU_MYSTICAL("main_menu_mystical.jpg"),
    MAIN_MENU_DARK("main_menu_dark.jpg"),
    MAIN_MENU_WINTER("main_menu_winter.jpg"),

    SETTINGS_EMPTY("settings_empty.jpg"),
    SETTINGS_GAME("settings_game.jpg"), SETTINGS_GRAPHICS("settings_graphics.jpg"),
    SETTINGS_AUDIO("settings_audio.jpg"), SETTINGS_LANGUAGE("settings_language.jpg");


    private String imageFile;

    Background(String imageFile) {
        this.imageFile = imageFile;
    }

    public Image getImage() {
        return new Image(Paths.get("src/main/resources/components/" + imageFile).toUri().toString());
    }

    @Override
    public String toString() {
        return imageFile;
    }
}
