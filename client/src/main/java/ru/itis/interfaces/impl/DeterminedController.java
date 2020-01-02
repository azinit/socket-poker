package ru.itis.interfaces.impl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import ru.itis.enums.Sound;
import ru.itis.interfaces.FireButtonDefiner;
import ru.itis.interfaces.SquadButtonDefiner;
import ru.itis.interfaces.StandardButtonDefiner;
import ru.itis.managers.MusicManager;
import ru.itis.managers.SoundManager;

import static ru.itis.managers.VFXManager.fade;

/*
    @ project:  Socket Poker
    @ module:   DeterminedController
    @ created:  12/15/2018
    @ by:       Ilya Azin
@ version   v.1.0
*/

public abstract class DeterminedController implements StandardButtonDefiner, SquadButtonDefiner, FireButtonDefiner {
//    private static final double VOLUME = 0.1;
    private static final double VOLUME = 0.1;
    
    /************ STANDARD BUTTON BEHAVIOUR ************/
    @Override
    public void standardMouseEntered(MouseEvent mouseEvent) {
        SoundManager.play(Sound.BTN_ENTERED, VOLUME);
        ////////////////////////////////////////////////
//        fade(mouseEvent, 1.0, 0.5, 1);
    }

    @Override
    public void standardMouseExited(MouseEvent mouseEvent) {
        ////////////////////////////////////////////////
//        fade(mouseEvent, 0.5, 1.0, 1);
    }

    @Override
    public void standardMousePressed(MouseEvent mouseEvent) {
        SoundManager.play(Sound.BTN_PRESSED, VOLUME);
        ////////////////////////////////////////////////
    }

    /************ SQUAD BUTTON BEHAVIOUR ************/
    // TODO: image to vector(SVG, EPS)

    @Override
    public void squadMouseEntered(MouseEvent mouseEvent) {
        SoundManager.play(Sound.BTN_ENTERED, VOLUME);
        ////////////////////////////////////////////////
        switchImages(mouseEvent, 0);
    }

    @Override
    public void squadMouseExited(MouseEvent mouseEvent) {
        switchImages(mouseEvent, 1);
    }

    @Override
    public void squadMousePressed(MouseEvent mouseEvent) {
        SoundManager.play(Sound.BTN_PRESSED, VOLUME);
        ////////////////////////////////////////////////
    }

    // 0: -> light; 1: -> base
    private void switchImages(MouseEvent mouseEvent, int state) {
        try {
            ImageView imageView = getImageView(mouseEvent);

            String fileName = "";
            switch (state) {
                case 0:
                    fileName = "h_" + getImagePath(mouseEvent, false);
                    break;
                case 1:
                    fileName = getImagePath(mouseEvent, false).substring(2);
                    break;
            }
//            System.out.println(fileName);
            String newImagePath = "/buttons/" + fileName;
            imageView.setImage(new Image(newImagePath));
        } catch (Exception e) {
        }
    }
    /************ FIRE BUTTON BEHAVIOUR ************/
    // TODO SOUNDS
    @Override
    public void fireMouseEntered(MouseEvent mouseEvent) {
        SoundManager.play(Sound.FIRE_IN, VOLUME);
        ////////////////////////////////////////////////
        fade(getImageView(mouseEvent), 0.2, 0.9, 1);
    }

    @Override
    public void fireMouseExited(MouseEvent mouseEvent) {
        SoundManager.play(Sound.FIRE_OUT, VOLUME);
        ////////////////////////////////////////////////
        fade(getImageView(mouseEvent), 0.9, 0.2, 1);
    }

    @Override
    public void fireMousePressed(MouseEvent mouseEvent) {
        SoundManager.play(Sound.FIRE_BLAST, VOLUME);
        ////////////////////////////////////////////////
        fade(getImageView(mouseEvent), 0.9, 1.0, 1);
    }


    /******************* PRIVATE *******************/
    private ImageView getImageView(MouseEvent mouseEvent) {
        return (ImageView) mouseEvent.getSource();
    }

    private String getImagePath(MouseEvent mouseEvent, boolean fullName) {
        String imagePath = ((ImageView) mouseEvent.getSource()).getImage().impl_getUrl();
        return (fullName) ? imagePath : imagePath.substring(imagePath.lastIndexOf("/") + 1);
    }
}
