package ru.itis.enums;

public enum Layout {
    INTRO_0("intro_0.fxml"),
    INTRO_1("intro_1.fxml"),
    INTRO_2("intro_2.fxml"),
    INTRO_3("intro_3.fxml"),

    MAIN_MENU("main_menu.fxml"),
    MAP_CHOICER("map_choicer.fxml"),
    SETTINGS("settings.fxml"),
    CREDITS("credits.fxml"),

    GAME("game.fxml"),
    TEST("test.fxml");

    private String FXMLFile;

    Layout(String FXMLFile) {
        this.FXMLFile = FXMLFile;
    }

    public String getFXMLFile() {
        return "/fxml/" + FXMLFile;
    }

    @Override
    public String toString() {
        return FXMLFile;
    }
}
