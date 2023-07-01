package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class RoomChatGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane chat = FXMLLoader.load(
                new URL(RoomChatGFX.class.getResource("/view/roomChat.fxml").toExternalForm()));
        if (stage.getScene() == null) {
            Scene scene = new Scene(chat);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chat);
        stage.show();
    }
}
