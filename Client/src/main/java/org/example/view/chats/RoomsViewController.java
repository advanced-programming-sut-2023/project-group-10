package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.chat.Message;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomsViewController implements ChatListControllerParent {
    public ListView<Label> chats;
    public Button addRoomButton;

    @FXML
    public void initialize() throws IOException {
        Packet packet = new Packet(ClientToServerCommands.GET_MY_ROOMS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chatIds = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        initChatList(chatIds);
    }

    @Override
    public void initChatList(ArrayList<String> chatIds) throws IOException {
        addRoomButton.setOnMouseClicked(this::addNemRoom);
        Packet packet = new Packet(ClientToServerCommands.GET_MY_ROOMS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chats = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        for (int i=chats.size()-1;i>=0;i++) {
            Label chatName=new Label(chats.get(i));
            this.chats.getItems().add(chatName);
            chatName.setOnMouseClicked(this::goToSpecificChat);
        }

    }

    @Override
    public void goToSpecificChat(MouseEvent mouseEvent) {
        String chatId=((Label)mouseEvent.getSource()).getText();
        RoomChatGFX roomChatGFX=new RoomChatGFX();
    }

    private void addNemRoom(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ChatMenuHomeGFX().start(SignupMenu.stage);
    }
}
