package org.example.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomizeMapMenuController {
    public Pane mapBox;
    public VBox itemsBox;
    public ScrollPane texturesBox;
    public ScrollPane treeTypesBox;
    public ScrollPane rockDirectionsBox;

    public void prepareGame(Stage primaryStage) {
        mapBox.setPrefWidth(primaryStage.getWidth() * 4 / 5);
        itemsBox.setPrefWidth(primaryStage.getWidth() / 5);
        texturesBox.setPrefHeight(primaryStage.getHeight()/2);
        treeTypesBox.setPrefHeight(primaryStage.getHeight()/4);
        rockDirectionsBox.setPrefHeight(primaryStage.getHeight()/4);
    }
}
