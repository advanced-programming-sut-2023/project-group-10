package org.example.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CommonGFXActions {
    public static void setMapScrollPaneProperties(ScrollPane mapBox) {
        mapBox.setPannable(true);
        mapBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() != MouseButton.PRIMARY) mapBox.setPannable(false);
        });
        mapBox.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> mapBox.setPannable(true));
    }
}
