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
        FXMLLoader loader = new FXMLLoader(new URL(ChatMenuHomeGFX.class.getResource("/view/chatMenuHome.fxml").toExternalForm()));
        Pane chatHome = loader.load();
        ((ChatHomeController) loader.getController()).setWidth(chatHome.getWidth());
        if (stage.getScene() == null) {
            Scene scene = new Scene(chatHome);
            stage.setScene(scene);
        } else
            stage.getScene().setRoot(chatHome);

        stage.setTitle("Chat Menu");
        stage.setMaximized(false);
        stage.show();
    }
}
