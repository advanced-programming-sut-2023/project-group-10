package org.example.view.chats;

import javafx.scene.input.MouseEvent;
import org.example.view.MainMenuGFX;
import org.example.view.SignupMenu;

public class ChatHomeController {
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
