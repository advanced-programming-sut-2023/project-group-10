package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class ShopMenuGFX extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane shopMenuPane = FXMLLoader.load(
                new URL(ShopMenuGFX.class.getResource("/view/shopMenu.fxml").toExternalForm()));
        this.stage = primaryStage;
        Scene scene = new Scene(shopMenuPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
