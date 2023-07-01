package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
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
    public Label nameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    public HBox idBox;
    private static String chatName;
    private ArrayList<Message> messagesCache;
    private ArrayList<String> blackListIDS = new ArrayList<>();
    Timeline updateMessages = new Timeline();

    public static void setChatName(String chatName) {
        PrivateChatController.chatName = chatName;
    }

    @FXML
    public void initialize() throws IOException {
        initID();
        //TODO put old messages,use process message func

        initChatBox(getMessages());
        Client.getInstance().getNotificationReceiver().setMessagesCache(messagesCache);
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
        updateMessages = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<Message> notifiersCache = Client.getInstance().getNotificationReceiver().getMessagesCache();
            if (messagesCache != notifiersCache) {
                messagesCache = notifiersCache;
                if (messagesCache != null) initChatBox(messagesCache);
            }
        }));
        updateMessages.setCycleCount(Timeline.INDEFINITE);
        updateMessages.play();
    }

    private void initID() {
        nameLabel.setText(chatName);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font("PT Mono", 24));
    }

    public void initChatBox(ArrayList<Message> messages) {
        chatBox.getChildren().clear();
        if (messages == null) return;
        for (Message message : messages)
            if (!blackListIDS.contains(message.getMessageID())) chatBox.getChildren().add(processMessage(message));
        chatScrollPane.setVvalue(1);
    }

    private void sendMessage() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message body", messageField.getText());
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        attributes.put("time sent", strResult);
        attributes.put("millies sent", String.valueOf(System.currentTimeMillis()));
        attributes.put("chat type", "private");
        attributes.put("chat id", chatName);
        Packet packet = new Packet(ClientToServerCommands.SEND_MESSAGE.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
    }

    private ArrayList<Message> getMessages() {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("requester", DataBank.getLoggedInUser().getUsername());
        attributes.put("other party", chatName);
        Packet packet = new Packet(ClientToServerCommands.GET_PRIVATE_CHAT_MESSAGES.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
            Packet receivedPacket = Client.getInstance().recievePacket();
            messagesCache = new Gson().fromJson(receivedPacket.getAttribute().get("messages"), new TypeToken<List<Message>>() {
            }.getType());
            return messagesCache;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HBox processMessage(Message message) {
        boolean isMine = message.getSender().getUsername().equals(DataBank.getLoggedInUser().getUsername());
        String strResult = message.getTimeSent();
        VBox messagePane = new VBox();
        HBox newMessage = new HBox();
        Label senderId = new Label(message.getSender().getUsername());
        //put Avatar-> I had errors
        Rectangle avatar = new Rectangle();
        avatar.setWidth(30);
        avatar.setHeight(30);
        avatar.setFill(new ImagePattern(new Image(message.getSender().getAvatar())));
        Label content = new Label(message.getMessageBody());
        Label time = new Label(strResult);
        //set time and format it
        messagePane.getChildren().addAll(senderId, content, time);
        if (isMine) {
            Button edit = new Button("edit");
            edit.setId(message.getMessageID());
            Button deleteForMe = new Button("del:m");
            deleteForMe.setId(message.getMessageID());
            Button deleteForEveryOne = new Button("del:e");
            deleteForEveryOne.setId(message.getMessageID());
            HBox container = new HBox(edit, deleteForEveryOne, deleteForMe);
            edit.setOnMouseClicked(this::editMessage);
            deleteForMe.setOnMouseClicked(this::deleteForMe);
            deleteForEveryOne.setOnMouseClicked(this::deleteForEveryOne);
            newMessage.getChildren().addAll(messagePane, avatar);
            messagePane.getChildren().addAll(container);
            newMessage.setAlignment(Pos.CENTER_RIGHT);
            messagePane.setBackground(Background.fill(Color.LIGHTGRAY));

        } else {
            newMessage.getChildren().addAll(avatar, messagePane);
            newMessage.setAlignment(Pos.CENTER_LEFT);
            messagePane.setBackground(Background.fill(Color.CORNFLOWERBLUE));

        }
        return newMessage;

    }

    private void deleteForEveryOne(MouseEvent mouseEvent) {
        String messageId = ((Button) mouseEvent.getSource()).getId();
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message id", messageId);
        attributes.put("chat type", "private");
        attributes.put("chat id", chatName);
        Packet packet = new Packet(ClientToServerCommands.DELETE_MESSAGE.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteForMe(MouseEvent mouseEvent) {
        String messageId = ((Button) mouseEvent.getSource()).getId();
        blackListIDS.add(messageId);
        initChatBox(messagesCache);
    }

    private void editMessage(MouseEvent mouseEvent) {
        String messageId = ((Button) mouseEvent.getSource()).getId();
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message id", messageId);
        attributes.put("chat type", "private");
        attributes.put("chat id", chatName);
        attributes.put("new body", messageField.getText());
        Packet packet = new Packet(ClientToServerCommands.EDIT_MESSAGE.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        updateMessages.stop();
        new PrivateChatsHomeGFX().start(SignupMenu.stage);
    }
}
