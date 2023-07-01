package org.example.view.chats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.connection.Client;

import java.net.URL;

public class PrivateChatGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(RoomChatGFX.class.getResource("/view/privateChat.fxml").toExternalForm()));
        Pane chat = loader.load();
        Client.getInstance().getNotificationReceiver().setChatController(loader.getController());
        if (stage.getScene() == null) {
            Scene scene = new Scene(chat);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chat);
        stage.show();
    }
}
