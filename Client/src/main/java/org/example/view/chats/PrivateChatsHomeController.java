package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateChatsHomeController implements ChatListControllerParent {
    public TextField searchBox;
    public Button searchButton;
    public ListView<Label> chats;
    String username;

    @FXML
    public void initialize() throws IOException {
        //TODO put old messages,use process message func
        Packet packet = new Packet(ClientToServerCommands.GET_MY_PRIVATE_CHATS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chatIds = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        initChatList(chatIds);
        searchButton.setOnMouseClicked(evt -> {

            username = searchBox.getText();
            addNewChat(searchBox.getText());
        });

    }

    private void addNewChat(String username) {
        //if it was successful invoke process chat
    }

    private void processChat(String username) {
        //add a child to list view-> add onMouseClicked for every label of the ListView
    }

    @Override
    public void initChatList(ArrayList<String> chatIds) throws IOException {
        searchButton.setOnMouseClicked(this::addNewChat);
        Packet packet = new Packet(ClientToServerCommands.GET_MY_ROOMS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        ArrayList<String> chats = new Gson().fromJson(receivedPacket.getAttribute().get("chats"), new TypeToken<List<String>>() {
        }.getType());
        for (int i = chats.size() - 1; i >= 0; i++) {
            Label chatName = new Label(chats.get(i));
            this.chats.getItems().add(chatName);
            chatName.setOnMouseClicked(this::goToSpecificChat);
        }


    }

    private void addNewChat(MouseEvent mouseEvent) {
        addNewChat(((Label) mouseEvent.getSource()).getText());
    }

    @Override
    public void goToSpecificChat(javafx.scene.input.MouseEvent mouseEvent) {

    }


}
