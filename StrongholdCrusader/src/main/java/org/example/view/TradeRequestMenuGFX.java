package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class TradeRequestMenuGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane requestPane = FXMLLoader.load(
                new URL(TradeRequestMenuGFX.class.getResource("/view/requestMenu.fxml").toExternalForm()));
        stage.getScene().setRoot(requestPane);
        stage.setTitle("making trade request");
        stage.setMaximized(true);
    }
}
