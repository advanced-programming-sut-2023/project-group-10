package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class CustomizeMapMenuGFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL customizeMapMenuFXML = CustomizeMapMenuGFX.class.getResource("/view/customizeMapMenu.fxml");
        FXMLLoader loader=new FXMLLoader(customizeMapMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = new Scene(rootPane);
        primaryStage.setScene(gameScene);
        ((CustomizeMapMenuController)loader.getController()).prepareGame(primaryStage);
    }
}
