package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class ShopMenuGFX extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane shopMenuPane = FXMLLoader.load(
                new URL(ShopMenuGFX.class.getResource("/view/shopMenu.fxml").toExternalForm()));
        this.stage = primaryStage;
        Image image = new Image(ShopMenuGFX.class.getResource("/images/backgrounds/brownPaper.jpeg").toExternalForm(), 1440 ,900, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        shopMenuPane.setBackground(new Background(backgroundImage));
        Scene scene = new Scene(shopMenuPane);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }



}
