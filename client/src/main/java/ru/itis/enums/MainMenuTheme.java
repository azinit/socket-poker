package ru.itis.enums;

import javafx.scene.image.Image;

public enum MainMenuTheme {
    VANILLA (Background.MAIN_MENU_VANILLA,  Music.BG_VANILLA),
    MYSTIC  (Background.MAIN_MENU_MYSTICAL, Music.BG_MYSTICAL),
    DARK    (Background.MAIN_MENU_DARK,     Music.BG_DARK),
    WINTER  (Background.MAIN_MENU_WINTER,   Music.BG_WINTER);

    private Background image;
    private Music music;

    MainMenuTheme(Background image, Music music) {
        this.image = image;
        this.music = music;
    }

    public static int size() {
        return values().length;
    }

    public Image getImage() {
        return image.getImage();
    }

    public Music getMusic() {
        return music;
    }
}
