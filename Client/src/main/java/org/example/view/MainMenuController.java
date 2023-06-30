package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainMenuController {
    public Text title;
    public Button profileButton;
    public Button gameButton;
    public Button logoutButton;
    public VBox buttons;
    public Button chatButton;

    @FXML
    public void initialize() {
        title.setTextAlignment(TextAlignment.CENTER);
        gameButton.setAlignment(Pos.CENTER);
        profileButton.setAlignment(Pos.CENTER);
        logoutButton.setAlignment(Pos.CENTER);
        chatButton.setAlignment(Pos.CENTER);
        buttons.setTranslateX(700);

    }

    public void goToProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(SignupMenu.stage);

    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        if (User.getUsers().size() < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not enough players");
            alert.setHeaderText("");
            alert.setContentText("you're the only person who has signed up!\nthere should at least be two users to start a game");
            alert.showAndWait();
        } else new StartGameMenu().start(SignupMenu.stage);

    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        org.example.controller.MainMenuController.logout();
        new LoginMenu().start(SignupMenu.stage);
    }

    public void goToChatMenu(MouseEvent mouseEvent) {
    }
}
