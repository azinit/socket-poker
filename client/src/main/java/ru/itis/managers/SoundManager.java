package ru.itis.managers;

import javafx.scene.media.AudioClip;
import ru.itis.enums.Sound;

/*
    @ project:  Socket Poker
    @ module:   SoundManager
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class SoundManager {
    // TODO: incVolume, ...
    // TODO: play with(Out)Stopping
    private static AudioClip currentSound;


    /////// SOUND METHOD SUMMARY \\\\\\\
    public static void play(Sound soundFile, double volume) {
        if (currentSound != null) {
            currentSound.stop();
        }

        currentSound = new AudioClip(soundFile.getPath());
        currentSound.play(volume);
    }

    public static void stop() {
        currentSound.stop();
    }

}

