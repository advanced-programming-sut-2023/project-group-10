package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.utils.RandomGenerator;

import java.net.URL;

public class StartGameMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL startGameMenuFXML = CustomizeMapMenuGFX.class.getResource("/view/startGameMenu.fxml");
        FXMLLoader loader = new FXMLLoader(startGameMenuFXML);
        Pane rootPane = loader.load();
        Background background = new Background(RandomGenerator.setBackground("/images/backgrounds/background9.jpeg"));
        rootPane.setBackground(background);
        Scene gameScene = primaryStage.getScene();
        gameScene.setRoot(rootPane);
        primaryStage.setMaximized(true);
        ((StartGameMenuController) loader.getController()).prepareMenu(primaryStage);
    }
}
