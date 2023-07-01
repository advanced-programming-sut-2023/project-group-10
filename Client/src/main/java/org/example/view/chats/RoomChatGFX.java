package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.connection.Client;

import java.net.URL;

public class RoomChatGFX extends Application {
    public  String roomName;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader(new URL(RoomChatGFX.class.getResource("/view/roomChat.fxml").toExternalForm()));
        Pane chat = loader.load();
        ((RoomChatController) loader.getController()).setRoomName(roomName);
        if (stage.getScene() == null) {
            Scene scene = new Scene(chat);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chat);
        stage.show();
    }
}
