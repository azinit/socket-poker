package ru.itis.managers;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import ru.itis.enums.Layout;

/*
    @ project:  Socket Poker
    @ module:   ScreenManager
    @ by:       Ilya Azin
    @ version   v.1.0
*/


public class ScreenManager {
    private static myLayout mainLayout = new myLayout();
    private static Parent loadedRoot;

    // Get mainLayout link
    public static myLayout getMainLayout() {
        return mainLayout;
    }

    // Set new Screen
    public static void setScreen(Layout layout) {
        if (loadScreen(layout)) {
            final DoubleProperty opacity = mainLayout.opacityProperty();

            if (!mainLayout.isEmpty()) {    // if layout is filled
                // oldScreen.fadeOut -> newScreen.fadeIn
                VFXManager.fade(opacity, 1, 0, 1,
                        onFinished -> refreshLayout());
            } else {
                // newScreen.switchOff
                mainLayout.setOpacity(0.0);
                mainLayout.add(loadedRoot);
                // newScreen.fadeIn
                VFXManager.fade(opacity, 0, 1, 2.5);
            }
        } else {
            System.out.println("SET SCREEN: cannot load " + layout.getFXMLFile());
        }
    }

    //////// LOADING SCREEN \\\\\\\\
    private static boolean loadScreen(Layout layout) {
        final String PREFIX = "LOAD SCREEN";
        String resource = layout.getFXMLFile();
        String name = layout.name();

        try {
            // load fxml
            FXMLLoader myLoader = new FXMLLoader(ScreenManager.class.getResource(resource));
            loadedRoot = myLoader.load();
            // log
            System.out.println(PREFIX + ": loaded " + name);
            return true;
        } catch (Exception e) {
            System.out.printf(PREFIX + ": something wrong with \"%s\": \"%s\"\n", name, resource);
            e.printStackTrace();
            return false;
        }
    }

    private static void refreshLayout() {
        mainLayout.remove(0);                                                               // remove the displayed screen
        mainLayout.add(0, loadedRoot);                                                      // add the screen
        VFXManager.fade(mainLayout.opacityProperty(), 0, 1, 0.8);      // fading screen
    }

    //////// ANONYMOUS CLASS \\\\\\\\
    private static class myLayout extends Parent {
        boolean isEmpty() {
            return getChildren().isEmpty();
        }

        void add(Node e) {
            getChildren().add(e);
        }

        void add(int index, Node e) {
            getChildren().add(index, e);
        }

        void remove(int index) {
            getChildren().remove(index);
        }
    }
}
