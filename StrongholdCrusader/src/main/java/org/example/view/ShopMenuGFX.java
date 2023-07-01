package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class ShopMenuGFX extends Application {

    public static Stage stage;
    public GameMenuGFXController gameMenuGFXController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL shopMenuFXML = ItemDetailsMenuGFX.class.getResource("/view/shopMenu.fxml");
        FXMLLoader loader = new FXMLLoader(shopMenuFXML);
        Pane shopMenuPane = loader.load();
        ((ShopMenuController) loader.getController()).setGameMenuGFXController(this.gameMenuGFXController);
        ShopMenuGFX.stage = primaryStage;
        Image image = new Image(ShopMenuGFX.class.getResource("/images/backgrounds/brownPaper.jpeg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        shopMenuPane.setBackground(new Background(backgroundImage));
        Scene scene = new Scene(shopMenuPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(shopMenuPane.getPrefWidth());
        stage.setHeight(shopMenuPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();
    }

    public void setGameController(GameMenuGFXController gameMenuGFXController) {
        this.gameMenuGFXController = gameMenuGFXController;
    }
}
