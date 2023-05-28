package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class PopularityFactorsMenuGFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL popularityFactorsMenuFXML = PopularityFactorsMenuGFX.class.getResource("/view/popularityFactorsMenu.fxml");
        FXMLLoader loader = new FXMLLoader(popularityFactorsMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = new Scene(rootPane);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        ((PopularityFactorsMenuController) loader.getController()).prepare(primaryStage);
    }
}
