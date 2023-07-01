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

public class PrivateChatsHomeController implements ChatListControllerParent {
    public TextField searchBox;
    public Button searchButton;
    public ListView<Label> chats;
    private ArrayList<String> chatsCache;
    String username;

    @FXML
    public void initialize() throws IOException {
        //TODO put old messages,use process message func
        Packet packet = new Packet(ClientToServerCommands.GET_MY_PRIVATE_CHATS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chatIds = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        Client.getInstance().getNotificationReceiver().setChatListCache(chatIds);
        chatsCache = chatIds;
        initChatList(chatIds);
        searchButton.setOnMouseClicked(evt -> {
            username = searchBox.getText();
            addNewChat(searchBox.getText());
        });


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

    private void addNewChat(String username) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("other party", username);
        Packet packet = new Packet(ClientToServerCommands.CAN_CREATE_PRIVATE_CHAT.getCommand(), attributes);
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
        boolean canCreate = Boolean.parseBoolean(receivedPacket.getAttribute().get("state"));
        if (!canCreate) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you can't create private chat with " + username);
            alert.setTitle("error");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("new private chat added");
            alert.setTitle("success");
            alert.showAndWait();
            attributes.clear();
            attributes.put("other party", username);
            packet = new Packet(ClientToServerCommands.CREATE_PRIVATE_CHAT.getCommand(), attributes);
            try {
                Client.getInstance().sendPacket(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        System.out.println("going to specific");
        PrivateChatGFX privateChatGFX = new PrivateChatGFX();
        privateChatGFX.setChatId(((Label) mouseEvent.getSource()).getText());
        try {
            privateChatGFX.start(SignupMenu.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ChatMenuHomeGFX().start(SignupMenu.stage);
    }
}
