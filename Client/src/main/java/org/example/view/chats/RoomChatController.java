package org.example.view.chats;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.model.chat.Message;
import org.example.view.SignupMenu;

import java.util.ArrayList;

public class RoomChatController implements ChatControllerParent {
    public Label roomNameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    String roomName;


    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @FXML
    public void initialize(){
        roomNameLabel.setText(roomName);

    }
    @Override

    public void initChatBox(ArrayList<Message> messages) {

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new RoomsViewGFX().start(SignupMenu.stage);
    }
}
