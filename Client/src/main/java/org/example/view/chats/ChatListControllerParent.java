package org.example.view.chats;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public interface ChatListControllerParent {
    void initChatList(ArrayList<String> chatIds) throws IOException;

    void goToSpecificChat(MouseEvent mouseEvent);


}
