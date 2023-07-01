package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class PrivateChatsHomeGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane list = FXMLLoader.load(
                new URL(PrivateChatGFX.class.getResource("/view/privateChatsList.fxml").toExternalForm()));
        if (stage.getScene() == null) {
            Scene scene = new Scene(list);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(list);
        stage.setTitle("Private Chats");
        stage.setHeight(700);
        stage.setWidth(500);
        stage.show();
    }
}
