package ru.itis.managers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


/*
    @ project:  Socket Poker
    @ module:   VFXManager
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class VFXManager {
    // by MouseEvent
    public static void fade(MouseEvent mouseEvent, double startValue, double endValue, double seconds) {
        fade((Node) mouseEvent.getSource(), startValue, endValue, seconds);
    }
    // by Node
    public static void fade(Node node, double startValue, double endValue, double seconds) {
        fade(node.opacityProperty(), startValue, endValue, seconds);
    }
    // Simple fading
    public static void fade(DoubleProperty opacity, double startValue, double endValue, double seconds) {
        fade(opacity, startValue, endValue, seconds, onFinished -> {});
    }
    // FULL FADING \\
    public static void fade(DoubleProperty opacity, double startValue, double endValue, double seconds, EventHandler<ActionEvent> onFinished) {
        KeyValue start = new KeyValue(opacity, startValue);
        KeyValue end = new KeyValue(opacity, endValue);

        new Timeline(
                new KeyFrame(Duration.ZERO, start),
                new KeyFrame(Duration.seconds(seconds), onFinished, end))
                .play();
    }

    public static void delay(double seconds, EventHandler<ActionEvent> onFinished) {
        DoubleProperty opacity = new StackPane().opacityProperty();
        KeyValue start = new KeyValue(opacity, 0);
        KeyValue end = new KeyValue(opacity, 1);

        new Timeline(
                new KeyFrame(Duration.ZERO, start),
                new KeyFrame(Duration.seconds(seconds), onFinished, end))
                .play();
    }

    public static void setBorder(ImageView imageView) {
        // TODO
        PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");

        BorderPane imageViewWrapper = new BorderPane(imageView);
        imageViewWrapper.getStyleClass().add("image-view-wrapper");

        BooleanProperty imageViewBorderActive = new SimpleBooleanProperty() {
            @Override
            protected void invalidated() {
                imageViewWrapper.pseudoClassStateChanged(imageViewBorder, get());
            }
        };

        imageView.setOnMouseClicked(ev -> imageViewBorderActive.set(!imageViewBorderActive.get()));

    }
}
