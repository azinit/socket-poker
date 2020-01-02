package ru.itis.enums;

import java.nio.file.Paths;

/*
    @ project:  Socket Poker
    @ module:   Sound
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public enum Music {
    BG_VANILLA  ("bg_vanilla.mp3",      "[VNL] MAIN MENU"),
    BG_MYSTICAL ("bg_mystical.mp3",     "[MST] MAIN MENU"),
    BG_DARK     ("bg_dark.mp3",         "[DRK] MAIN MENU"),
    BG_WINTER   ("bg_winter.mp3",       "[WNT] MAIN MENU"),

    FIGHT_BG("fight_bg.mp3", "BATTLE"),
    BG_POKER("poker.mp3", "POKER GAME");

    private String soundFile;
    private String situation;

    Music(String soundFile, String situation) {
        this.soundFile = soundFile;
        this.situation = situation;
    }

    public String getPath() {
        return Paths.get("src/main/resources/audio/music/" + soundFile).toUri().toString();
    }

    public String getSituation() {
        return situation;
    }

    @Override
    public String toString() {
        return soundFile;
    }
}
