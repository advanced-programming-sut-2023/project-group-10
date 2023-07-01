package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.connection.Client;

import java.net.URL;

public class PublicChatGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(ChatMenuHomeGFX.class.getResource("/view/publicChatMenu.fxml").toExternalForm()));
        Pane root = loader.load();
        if (stage.getScene() == null) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(root);
        stage.setTitle("Public Chat");
        stage.setHeight(700);
        stage.setWidth(500);
        stage.show();
    }
}
