package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class BriefingGFX extends Application {

    public static Stage stage;
    public GameMenuGFXController gameMenuGFXController;

    public void setGameMenuGFXController(GameMenuGFXController gameMenuGFXController) {

        this.gameMenuGFXController = gameMenuGFXController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BriefingGFX.stage = stage;
        URL briefingFXML = BriefingGFX.class.getResource("/view/briefingMenu.fxml");
        FXMLLoader loader = new FXMLLoader(briefingFXML);
        Pane briefingPane = loader.load();
        javafx.scene.image.Image image = new javafx.scene.image.Image(ShopMenuGFX.class.getResource("/images/backgrounds/briefingBackground.png")
                .toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        briefingPane.setBackground(new Background(backgroundImage));
        Scene scene = new Scene(briefingPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(briefingPane.getPrefWidth());
        stage.setHeight(briefingPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();


    }

}
