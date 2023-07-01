package org.example.view.chats;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.model.chat.Message;

import java.util.ArrayList;

public class RoomChatController implements ChatControllerParent {
    public Label roomName;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;

    @Override
    public void initChatBox(ArrayList<Message> messages) {

    }
}
