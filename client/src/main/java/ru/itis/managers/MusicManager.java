package ru.itis.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.itis.enums.Music;
import ru.itis.enums.Sound;

/*
    @ project:  Socket Poker
    @ module:   MusicManager
    @ created:  12/15/2018  
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class MusicManager {
    // TODO: incVolume, ...
    private static final String PREFIX = "\uD83C\uDFB6 ";
    private static MediaPlayer mediaPlayer;
    private static Music currentMusic;
    private static Media currentMedia;

    private static boolean isPlayerOn = false;

    /////// GETTERS && SETTERS \\\\\\\
    public static boolean isPlayerOn() {
        return isPlayerOn;
    }

    private static void setCurrentMedia(Media newMusic) {
        currentMedia = newMusic;
    }

    /////// MUSIC METHOD SUMMARY \\\\\\\
    public static void play(Music musicFile) {
        // Stop old music
        if (isPlayerExist()) {
            // If requested the same music
            if (currentMusic.equals(musicFile) && isPlayerOn) {
                return;
            }
            mediaPlayer.stop();
        }
        // Create new music
        setCurrentMedia(new Media(musicFile.getPath()));
        currentMusic = musicFile;
        // Create new player
        mediaPlayer = new MediaPlayer(currentMedia);
        mediaPlayer.setCycleCount(3);
        mediaPlayer.setVolume(0.3);
        setAutoPlay();
        // Log info
        log();
    }

    public static void play() {
        if (isPlayerExist()) {
            mediaPlayer.play();
            isPlayerOn = true;
        }
    }

    public static void setAutoPlay() {
        if (isPlayerExist()) {
            mediaPlayer.setAutoPlay(true);
            isPlayerOn = true;
        }
    }

    public static void pause() {
        if (isPlayerExist() && isPlayerOn) {
            mediaPlayer.pause();
            isPlayerOn = false;
        }
    }

    public static void stop() {
        if (isPlayerExist()) {
            mediaPlayer.stop();
            isPlayerOn = false;
        }
    }

    private static boolean isPlayerExist() {
        return mediaPlayer != null;
    }

    private static void log() {
        System.out.println(PREFIX + currentMusic.getSituation());
    }

}
