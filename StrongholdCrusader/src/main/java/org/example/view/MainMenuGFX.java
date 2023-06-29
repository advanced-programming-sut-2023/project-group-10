package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.utils.RandomGenerator;

import java.net.URL;

public class MainMenuGFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane mainMenu = FXMLLoader.load(
                new URL(MainMenuGFX.class.getResource("/view/mainMenu.fxml").toExternalForm()));
        mainMenu.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/background11.jpeg")));
        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(mainMenu);
            primaryStage.setScene(scene);
        } else
            primaryStage.getScene().setRoot(mainMenu);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
