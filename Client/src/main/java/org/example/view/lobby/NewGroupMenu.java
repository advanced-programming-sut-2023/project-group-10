package org.example.view.lobby;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class NewGroupMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(NewGroupMenu.class.getResource("/view/newGroupMenu.fxml").toExternalForm()));
        Pane root = loader.load();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
