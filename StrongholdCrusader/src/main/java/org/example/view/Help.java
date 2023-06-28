package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class Help extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        URL helpFXML = Help.class.getResource("/view/helpMenu.fxml");
        FXMLLoader loader = new FXMLLoader(helpFXML);
        Pane helpPane= loader.load();
        helpPane.setStyle("-fx-background-color: rgba(0, 0, 0, 1); -fx-background-radius: 10;");
        Scene scene = new Scene(helpPane);
        stage.setOpacity(0.7);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(helpPane.getPrefWidth());
        stage.setHeight(helpPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();
    }


}
