package ru.itis.entities;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/*
    @ project:  Socket Poker
    @ module:   Layouts
    @ by:       Ilya Azin
    @ version   v.1.0 
*/
public class Layout extends Parent {
    public boolean isEmpty() {
        return getChildren().isEmpty();
    }

    public void add(Node e){
        getChildren().add(e);
    }

    public void add(int index, Node e){
        getChildren().add(index, e);
    }

    public void remove(int index){
        getChildren().remove(index);
    }

    public DoubleProperty getOpacityProperty() {
        return opacityProperty();
    }
}
