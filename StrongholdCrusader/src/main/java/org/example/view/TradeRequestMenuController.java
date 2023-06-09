package org.example.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;

import java.util.ArrayList;

public class TradeRequestMenuController {
    public static VBox panels = new VBox();
    private HBox usersAndSelectedOne = new HBox();
    private ListView<HBox> userList = new ListView<>();
    private User selectedUser = null;
    private Label selectedUserLabel = new Label();


    @FXML
    public void initialize() {
        showUsers();
        showSelectedUser();
        showUsersResources();

    }

    private void showUsers() {
        ArrayList<HBox> usersBox = new ArrayList<>();

        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            HBox userInfo = new HBox();
            Text username = new Text();
            username.setText(government.getOwner().getUsername());
            username.setOnMouseClicked(this::selectUser);
            userInfo.getChildren().add(username);
            usersBox.add(userInfo);
        }
        userList.setItems((ObservableList<HBox>) usersBox);
        usersAndSelectedOne.getChildren().add(userList);
        panels.getChildren().add(usersAndSelectedOne);


    }

    private void selectUser(MouseEvent mouseEvent) {
    }

    private void showUsersResources() {


    }

    private void showSelectedUser() {

    }


}
