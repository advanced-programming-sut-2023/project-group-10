package org.example.view.chats;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.view.MainMenuGFX;
import org.example.view.SignupMenu;

public class ChatHomeController {
    public VBox buttons;
    public double width;

    public void setWidth(double width) {
        this.width = width;
    }

    @FXML
    public void initialize(){
        buttons.setTranslateX(480);
    }

    public void goToPublicChat(MouseEvent mouseEvent) throws Exception {
        new PublicChatGFX().start(SignupMenu.stage);
    }

    public void goToPrivateChat(MouseEvent mouseEvent) throws Exception {
        new PrivateChatsHomeGFX().start(SignupMenu.stage);
    }

    public void goToRooms(MouseEvent mouseEvent) throws Exception {
        new RoomsViewGFX().start(SignupMenu.stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenuGFX().start(SignupMenu.stage);
    }

}
