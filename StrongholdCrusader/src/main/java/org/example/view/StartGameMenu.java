package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class StartGameMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO: don't open start game menu if registered users are less than 2
        URL startGameMenuFXML = CustomizeMapMenuGFX.class.getResource("/view/startGameMenu.fxml");
        FXMLLoader loader = new FXMLLoader(startGameMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = primaryStage.getScene();
        gameScene.setRoot(rootPane);
        primaryStage.setMaximized(true);
        ((StartGameMenuController) loader.getController()).prepareMenu(primaryStage);
    }
}
