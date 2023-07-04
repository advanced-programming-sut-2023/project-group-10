package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
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

public class RoomChatController implements ChatControllerParent {
    public Label roomNameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    public VBox topBox;
    private static String roomName;
    private ArrayList<String> blackListIDS = new ArrayList<>();
    private ArrayList<Message> messagesCache;
    private TextField nameField;
    private Button addUser;
    private static boolean isForGame = false;

    public static void setForGame(boolean forGame) {
        isForGame = forGame;
    }

    public static void setRoomName(String roomName) {
        RoomChatController.roomName = roomName;
    }

    @FXML
    public void initialize() throws IOException {
        roomNameLabel.setText(roomName);
        if (!isForGame) {
            try {
                initTopBox();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        Timeline updateMessages = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<Message> notifiersCache = Client.getInstance().getNotificationReceiver().getMessagesCache();
            if (messagesCache != notifiersCache) {
                messagesCache = notifiersCache;
                if (messagesCache != null) initChatBox(messagesCache);
            }
        }));
        updateMessages.setCycleCount(Timeline.INDEFINITE);
        updateMessages.play();
    }

    @Override

    public void initChatBox(ArrayList<Message> messages) {
        chatBox.getChildren().clear();
        for (Message msg : messages)
            if (!blackListIDS.contains(msg.getMessageID())) chatBox.getChildren().add(processMessage(msg));
        chatScrollPane.setVvalue(1);
    }

    private void initTopBox() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("room id", roomName);
        Packet packet = new Packet(ClientToServerCommands.IS_ADMIN.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        boolean isAdmin = Boolean.parseBoolean(receivedPacket.getAttribute().get("state"));
        if (!isAdmin)
            return;
        HBox top = new HBox();
        nameField = new TextField();
        nameField.setPromptText("enter user name to add to the chat");
        addUser = new Button("add");
        addUser.setOnMouseClicked(event -> addUserToRoom());
        top.getChildren().addAll(nameField, addUser);
        topBox.getChildren().add(top);
    }

    private void addUserToRoom() {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("room id", roomName);
        attributes.put("username", nameField.getText());
        Packet packet = new Packet(ClientToServerCommands.ADD_MEMBER_TO_ROOM.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Packet responose = Client.getInstance().recievePacket();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(responose.getAttribute().get("message"));
            alert.setTitle("");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new RoomsViewGFX().start(SignupMenu.stage);
    }

    private void sendMessage() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message body", messageField.getText());
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        attributes.put("time sent", strResult);
        attributes.put("millies sent", String.valueOf(System.currentTimeMillis()));
        attributes.put("chat type", "room");
        attributes.put("chat id", roomName);
        Packet packet = new Packet(ClientToServerCommands.SEND_MESSAGE.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
    }

    private ArrayList<Message> getMessages() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("room id", roomName);
        Packet packet = new Packet(ClientToServerCommands.GET_ROOM_MESSAGES.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        messagesCache = new Gson().fromJson(receivedPacket.getAttribute().get("messages"), new TypeToken<List<Message>>() {
        }.getType());
        return messagesCache;
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
        //    DELETE_MESSAGE("delete message", "message id", "chat type", "chat id")
        attributes.put("message id", messageId);
        attributes.put("chat type", "room");
        attributes.put("chat id", roomName);
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
        //    EDIT_MESSAGE("edit message", "message id", "chat type", "chat id", "new body");
        attributes.put("message id", messageId);
        attributes.put("chat type", "room");
        attributes.put("chat id", roomName);
        attributes.put("new body", messageField.getText());
        Packet packet = new Packet(ClientToServerCommands.EDIT_MESSAGE.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
