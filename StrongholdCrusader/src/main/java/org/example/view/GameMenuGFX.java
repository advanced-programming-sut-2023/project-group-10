package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class GameMenuGFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL gameMenuFXML = GameMenuGFX.class.getResource("/view/gameMenu.fxml");
        FXMLLoader loader=new FXMLLoader(gameMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = new Scene(rootPane);
        primaryStage.setScene(gameScene);
        ((GameMenuController)loader.getController()).prepareGame(primaryStage);
    }

}
