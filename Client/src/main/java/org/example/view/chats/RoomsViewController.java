package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomsViewController implements ChatListControllerParent {
    public ListView<Label> chats;
    public TextField roomIdField;
    public Button addRoomButton;
    private ArrayList<String> chatsCache;

    @FXML
    public void initialize() throws IOException {
        Packet packet = new Packet(ClientToServerCommands.GET_MY_ROOMS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chatIds = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        Client.getInstance().getNotificationReceiver().setChatListCache(chatIds);
        chatsCache = chatIds;
        initChatList(chatIds);
        addRoomButton.setOnMouseClicked(event -> addNewRoom());


        Timeline updateChats = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<String> notifiersCache = Client.getInstance().getNotificationReceiver().getChatListCache();
            if (chatsCache != notifiersCache) {
                chatsCache = notifiersCache;
                if (chatsCache != null) initChatList(chatsCache);
            }
        }));
        updateChats.setCycleCount(Timeline.INDEFINITE);
        updateChats.play();
    }

    @Override
    public void initChatList(ArrayList<String> chatIds) {
        chats.getItems().clear();
        if (chatIds == null || chatIds.size() == 0)
            return;
        for (int i = chatIds.size() - 1; i >= 0; i--) {
            Label chatName = new Label(chatIds.get(i));
            chatName.setFont(new Font("PT Mono", 20));
            this.chats.getItems().add(chatName);
            chatName.setOnMouseClicked(this::goToSpecificChat);
        }
    }

    @Override
    public void goToSpecificChat(MouseEvent mouseEvent) {
        RoomChatGFX roomChatGFX = new RoomChatGFX();
        roomChatGFX.setRoomName(((Label) mouseEvent.getSource()).getText());
        try {
            roomChatGFX.start(SignupMenu.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewRoom() {
        String roomId = roomIdField.getText();
        if (roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("enter a name for the room first!");
            alert.setTitle("error");
            alert.showAndWait();
        }
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("room id", roomId);
        Packet packet = new Packet(ClientToServerCommands.IS_ROOM_ID_VALID.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet receivedPacket = null;
        try {
            receivedPacket = Client.getInstance().recievePacket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isValid = Boolean.parseBoolean(receivedPacket.getAttribute().get("state"));
        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("a room with this name already exists! try again with a different name.");
            alert.setTitle("error");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("new room added");
            alert.setTitle("success");
            alert.showAndWait();
            attributes.clear();
            attributes.put("room id", roomId);
            packet = new Packet(ClientToServerCommands.CREATE_ROOM.getCommand(), attributes);
            try {
                Client.getInstance().sendPacket(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ChatMenuHomeGFX().start(SignupMenu.stage);
    }
}
