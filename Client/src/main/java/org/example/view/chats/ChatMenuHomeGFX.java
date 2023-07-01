package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class ChatMenuHomeGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane chatHome = FXMLLoader.load(
                new URL(ChatMenuHomeGFX.class.getResource("/view/chatMenuHome.fxml").toExternalForm()));
        if (stage.getScene() == null) {
            Scene scene = new Scene(chatHome);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chatHome);
        stage.setMaximized(false);
        stage.show();
    }
}
