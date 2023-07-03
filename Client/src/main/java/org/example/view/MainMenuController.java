package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.view.chats.ChatMenuHomeGFX;
import org.example.view.lobby.LobbyHomeGFX;

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
        buttons.setSpacing(30);

    }

    public void goToProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(SignupMenu.stage);

    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        new LobbyHomeGFX().start(SignupMenu.stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        Packet packet = new Packet(ClientToServerCommands.LOGOUT.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Client.getInstance().recievePacket();
        Client.getInstance().logout();
        new LoginMenu().start(SignupMenu.stage);
    }

    public void goToChatMenu(MouseEvent mouseEvent) throws Exception {
        new ChatMenuHomeGFX().start(SignupMenu.stage);
    }
}
