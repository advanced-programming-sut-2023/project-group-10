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
import org.example.view.DataBank;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PrivateChatController implements ChatControllerParent {
    public Rectangle avatar;
    public Label nameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    public HBox idBox;
    private String chatName;

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @FXML
    public void initialize() throws IOException {
        initID();
        //TODO put old messages,use process message func
        initChatBox(getMessages());
        add.setOnMouseClicked(evt -> {
            try {
                sendMessage();
                messageField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clear.setOnMouseClicked(evt -> {
            messageField.setText("");
        });
    }

    private void initID() {
        nameLabel.setText(chatName);
    }

    public void initChatBox(ArrayList<Message> messages) {
        chatBox.getChildren().clear();
        for (Message message : getMessages())
            chatBox.getChildren().add(processMessage(message));
        chatScrollPane.setVvalue(1);
    }

    private void sendMessage() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message body", messageField.getText());
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        attributes.put("time sent", strResult);
        attributes.put("chat type", "private");
        // TODO: get actual id from last menu
        attributes.put("chat id", "place holder");
        Packet packet = new Packet(ClientToServerCommands.SEND_MESSAGE.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
    }

    private ArrayList<Message> getMessages() {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("requester", DataBank.getUsername());
        // TODO: get actual id from last menu
        attributes.put("other party", "place holder");
        Packet packet = new Packet(ClientToServerCommands.GET_PRIVATE_CHAT_MESSAGES.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
            Packet receivedPacket = Client.getInstance().recievePacket();
            return new Gson().fromJson(receivedPacket.getAttribute().get("messages"), new TypeToken<List<Message>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox processMessage(Message message) {
        boolean isMine = message.getSender().getUsername().equals(DataBank.getLoggedInUser().getUsername());
        String strResult = message.getTimeSent();
        VBox messagePane = new VBox();
        Label content = new Label(message.getMessageBody());
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

    public void back(MouseEvent mouseEvent) throws Exception {
        new PrivateChatsHomeGFX().start(SignupMenu.stage);
    }
}
