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
    public boolean isForGame=false;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setForGame(boolean forGame) {
        isForGame = forGame;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader(new URL(RoomChatGFX.class.getResource("/view/roomChat.fxml").toExternalForm()));
        RoomChatController.setRoomName(roomName);
        RoomChatController.setForGame(this.isForGame);
        Pane chat = loader.load();
        if (stage.getScene() == null) {
            Scene scene = new Scene(chat);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chat);
        stage.setTitle("Room");
        stage.setHeight(700);
        stage.setWidth(500);
        stage.show();
    }
}
