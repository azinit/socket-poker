package ru.itis.enums;

/*
    @ project:  Socket Poker
    @ module:   Sound
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public enum Sound {
    BTN_PRESSED("btn_pressed.mp3"), BTN_ENTERED("btn_entered.mp3"),
    FIRE_IN("fire_in_2.mp3"), FIRE_OUT("fire_out_2.mp3"), FIRE_BLAST("fire_blast_2.mp3");

    private String soundFile;

    Sound(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getPath() {
        return "file:src/main/resources/audio/sound/" + soundFile;
    }

    @Override
    public String toString() {
        return soundFile;
    }
}
