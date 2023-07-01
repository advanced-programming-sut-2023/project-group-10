package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomsViewController implements ChatListControllerParent {
    public ListView<HBox> chats;
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
    public void initChatList(ArrayList<String> chatIds) {

    }
}
