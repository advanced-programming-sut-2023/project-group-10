package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.chat.Message;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrivateChatController implements ChatControllerParent {
    public Rectangle avatar;
    public Label nicknameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    public HBox idBox;
    private String message;

    @FXML
    public void initialize() throws IOException {
        initID();
        //TODO put old messages,use process message func
        Packet packet = new Packet(ClientToServerCommands.GET_PRIVATE_CHAT_MESSAGES.getCommand(), );
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<Message> messages = new Gson().fromJson(receivedPacket.getAttribute().get("messages"), new TypeToken<List<Message>>() {
        }.getType());
        initChatBox(messages);
        add.setOnMouseClicked(evt -> {

            message = messageField.getText();
            chatBox.getChildren().add(processMessage(message, true));
            chatScrollPane.setVvalue(1);

        });
        clear.setOnMouseClicked(evt -> {
            message = "";
            messageField.setText("");
        });

    }

    private void initID() {

    }

    public void initChatBox(ArrayList<Message> messages) {
        //old chats
        chatScrollPane.setVvalue(1);
    }

    private VBox processMessage(String message, boolean isMine) {
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        VBox messagePane = new VBox();
        Label content = new Label(message);
        Label time = new Label(strResult);
        messagePane.getChildren().addAll(content, time);

        if (isMine) {
            Button edit = new Button("edit");
            Button deleteForMe = new Button("del:m");
            Button deleteForEveryOne = new Button("del:e");
            edit.setOnMouseClicked(this::editMessage);
            deleteForMe.setOnMouseClicked(this::deleteForMe);
            deleteForEveryOne.setOnMouseClicked(this::deleteForEveryOne);
            HBox buttons = new HBox();
            buttons.getChildren().addAll(edit, deleteForMe, deleteForEveryOne);
            messagePane.getChildren().addAll(messagePane, buttons);
        }
        return messagePane;

    }

    private void deleteForEveryOne(MouseEvent mouseEvent) {
    }

    private void deleteForMe(MouseEvent mouseEvent) {
    }

    private void editMessage(MouseEvent mouseEvent) {
    }
}
