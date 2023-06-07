package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MainMenuController {
    public Text title;
    public Button profileButton;
    public Button gameButton;
    public Button logoutButton;

    @FXML
    public void initialize() {
        title.setLayoutX(500);
        title.setLayoutY(100);
        gameButton.setLayoutX(630);
        gameButton.setLayoutY(180);
        profileButton.setLayoutX(630);
        profileButton.setLayoutY(290);
        logoutButton.setLayoutX(630);
        logoutButton.setLayoutY(400);
    }

    public void goToProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(SignupMenu.stage);

    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        new StartGameMenu().start(SignupMenu.stage);

    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        org.example.controller.MainMenuController.logout();
        new LoginMenu().start(SignupMenu.stage);
    }
}
