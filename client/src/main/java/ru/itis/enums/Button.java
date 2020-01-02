package ru.itis.enums;

import javafx.scene.image.Image;

import java.nio.file.Paths;

public enum Button {
    ;


    private String imageFile;

    Button(String imageFile) {
        this.imageFile = imageFile;
    }

    public Image getImage() {
        return new Image(Paths.get("src/main/resources/buttons/" + imageFile).toUri().toString());
//        return new Image(Paths.get("/buttons/" + imageFile).toUri().toString());
    }

    @Override
    public String toString() {
        return imageFile;
    }
}
