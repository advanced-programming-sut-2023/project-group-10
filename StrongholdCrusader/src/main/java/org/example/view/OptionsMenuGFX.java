package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class OptionsMenuGFX extends Application {
    public static Stage stage;
    public GameMenuGFXController gameMenuGFXController;

    public void setGameMenuGFXController(GameMenuGFXController gameMenuGFXController) {
        this.gameMenuGFXController = gameMenuGFXController;
    }


    @Override
    public void start(Stage stage) throws Exception {
        OptionsMenuGFX.stage = stage;
        URL optionsGFX = OptionsMenuGFX.class.getResource("/view/optionsMenu.fxml");
        FXMLLoader loader = new FXMLLoader(optionsGFX);
        Pane optionsPane = loader.load();
        ((OptionsMenuController) loader.getController()).setGameMenuGFXController(this.gameMenuGFXController);
        optionsPane.setStyle("-fx-background-color: rgba(80, 80, 80, 0.5); -fx-background-radius: 10;");
        Scene scene = new Scene(optionsPane);
        stage.setOpacity(0.7);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(optionsPane.getPrefWidth());
        stage.setHeight(optionsPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();
    }


}
