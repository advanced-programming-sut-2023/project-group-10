package org.example.view.lobby;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.view.chats.ChatMenuHomeGFX;

import java.net.URL;

public class LobbyHomeGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(LobbyHomeGFX.class.getResource("/view/lobbyHome.fxml").toExternalForm()));
        Pane root = loader.load();
        stage.getScene().setRoot(root);
        stage.centerOnScreen();
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setTitle("Lobby");
        stage.show();
    }
}
