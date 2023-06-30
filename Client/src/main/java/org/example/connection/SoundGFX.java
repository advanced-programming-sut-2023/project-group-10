package org.example.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.view.GameMenuGFXController;
import org.example.view.SoundController;

import java.net.URL;

public class SoundGFX extends Application {
    public static Stage stage;
    private GameMenuGFXController gameMenuGFXController;

    public void setGameMenuGFXController(GameMenuGFXController gameMenuGFXController) {
        this.gameMenuGFXController = gameMenuGFXController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        SoundGFX.stage = stage;
        URL soundFXML = SoundGFX.class.getResource("/view/soundMenu.fxml");
        FXMLLoader loader = new FXMLLoader(soundFXML);
        System.out.println(loader.getController() == null);
        Pane soundPane = loader.load();
        ((SoundController) loader.getController()).setGameMenuGFXController(this.gameMenuGFXController);
        soundPane.setStyle("-fx-background-color: rgba(80, 80, 80, 0.5); -fx-background-radius: 10;");
        Scene scene = new Scene(soundPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(soundPane.getPrefWidth());
        stage.setHeight(soundPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();

    }
}
