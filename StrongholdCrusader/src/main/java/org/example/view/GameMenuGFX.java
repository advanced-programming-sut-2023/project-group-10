package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.controller.GameMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

import java.net.URL;
import java.util.HashMap;

public class GameMenuGFX extends Application {
    public GameMenuGFX(HashMap<String, Color> colors, HashMap<String, Coordinate> keeps, Map map) throws Exception {
        GameMenuController.initializeGame(colors, keeps, map);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL gameMenuFXML = GameMenuGFX.class.getResource("/view/gameMenu.fxml");
        FXMLLoader loader = new FXMLLoader(gameMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = primaryStage.getScene();
        gameScene.setRoot(rootPane);
        primaryStage.setMaximized(true);
        ((GameMenuGFXController) loader.getController()).prepareGame(primaryStage);
    }
}
