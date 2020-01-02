package ru.itis.entities;

import ru.itis.enums.MainMenuTheme;

/*
    @ project:  Socket Poker
    @ module:   User
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class Config {
    // GRAPHICS
//    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_WIDTH = 1280;
//    public final static int WINDOW_HEIGHT = 720;
    public final static int WINDOW_HEIGHT = 720;

    // MUSIC
    public static boolean wishMusic = true;

    // THEMES
    public static int themeID = -1;

    public static MainMenuTheme getTheme() {
        return MainMenuTheme.values()[themeID];
    }
    // BUILT-IN
    //    public static boolean inMenuHierarchy = false;
}
