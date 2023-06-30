package org.example.connection.Handlers;

import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.controller.ChatController;
import org.example.model.chat.Message;

import java.util.ArrayList;

public class ChatHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public ChatHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void enterPublicChat() {
        String username = receivedPacket.getAttribute().get("username");
        ArrayList<Message> messages = ChatController.enterChat("public_chat", username);
    }


}