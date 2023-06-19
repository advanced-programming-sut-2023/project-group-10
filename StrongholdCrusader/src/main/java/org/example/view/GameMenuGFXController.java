package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class GameMenuGFXController {
    public Pane mapBox;
    public HBox controlBox;
    public ListView<Circle> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;

    public void prepareGame(Stage stage) throws URISyntaxException {
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
        controlBox.setStyle("-fx-background-color: #171817");
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        mapBox.setStyle("-fx-background-color: #796e59");
        buildingBox.setPrefWidth(stage.getWidth()*4/6);
        miniMapBox.setPrefWidth(stage.getWidth() / 6);
        miniMapBox.setStyle("-fx-background-color: #6c6cb4");
        infoBox.setPrefWidth(stage.getWidth() / 6);
        infoBox.setStyle("-fx-background-color: #ee9a73");
        File path = new File(GameMenuGFXController.class.getResource("/images/buildings").toURI());
        String[] files = path.list();
        for(int i = 0; i < files.length; i++)
            buildingBox.getItems().add(new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + files[i]).toString()))));
    }
}