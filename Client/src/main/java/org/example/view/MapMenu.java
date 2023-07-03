package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.lobby.Group;

import java.net.URL;

public class MapMenu extends Application {
    static Group currentGroup;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(MapMenu.class.getResource("/view/mapMenu.fxml").toExternalForm()));
        Pane root = loader.load();
        stage.getScene().setRoot(root);
        stage.show();
    }

    public static void setGroup(Group group) {
        currentGroup = group;
    }
}
