package org.example.view.lobby;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.lobby.Group;

import java.net.URL;

public class GroupWaitingMenu extends Application {
    static Group targetGroup;

    public GroupWaitingMenu(Group targetGroup) {
        GroupWaitingMenu.targetGroup = targetGroup;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new URL(GroupWaitingMenu.class.getResource("/view/groupWaitingMenu.fxml").toExternalForm()));
        Pane root = loader.load();
        stage.getScene().setRoot(root);
        stage.centerOnScreen();
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setTitle("waiting menu");
        stage.show();
    }
}
