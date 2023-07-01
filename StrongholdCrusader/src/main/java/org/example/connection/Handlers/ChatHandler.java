package org.example.connection.Handlers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.ConnectionDatabase;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.ChatController;
import org.example.model.chat.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public ChatHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void canCreatePrivateChat() throws IOException {
        String requester = connection.getUsername();
        String otherParty = receivedPacket.getAttribute().get("other party");
        boolean state = ChatController.canCreatePrivateChat(requester, otherParty);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("state", String.valueOf(state));
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_CREATE_CHAT.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void createPrivateChat() throws IOException {
        String requester = connection.getUsername();
        String otherParty = receivedPacket.getAttribute().get("other party");
        ChatController.createPrivateChat(requester, otherParty);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("chat type", "private");
        attributes.put("chat id", otherParty);
        Packet toBeSentToRequester = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), attributes);
        connection.sendPacket(toBeSentToRequester);
        Connection otherConnection = ConnectionDatabase.getInstance().getConnectionByUsername(otherParty);
        if (otherConnection != null) {
            HashMap<String, String> attributes2 = new HashMap<>();
            attributes2.put("chat type", "private");
            attributes2.put("chat id", requester);
            Packet toBeSentToOtherParty = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), attributes2);
            otherConnection.sendPacket(toBeSentToOtherParty);
        }
    }

    public void isRoomIDValid() throws IOException {
        String roomID = receivedPacket.getAttribute().get("room id");
        boolean state = ChatController.isRoomIDValid(roomID);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("state", String.valueOf(state));
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_CREATE_CHAT.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void creatRoom() throws IOException {
        String admin = connection.getUsername();
        String roomID = receivedPacket.getAttribute().get("room id");
        ChatController.createRoom(admin, roomID);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("chat type", "room");
        attributes.put("room id", roomID);
        Packet toBeSent = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void getMyPrivateChats() throws IOException {
        String username = connection.getUsername();
        ArrayList<String> myPrivateChats = ChatController.getMyPrivateChats(username);
        connection.sendPacket(getChatsPacket(myPrivateChats));
    }

    public void getMyRooms() throws IOException {
        String username = connection.getUsername();
        ArrayList<String> myRooms = ChatController.getMyRooms(username);
        connection.sendPacket(getChatsPacket(myRooms));
    }

    public void getPublicChatMessages() throws IOException {
        String username = connection.getUsername();
        ArrayList<Message> messages = ChatController.getPublicChatMessages(username);
        connection.sendPacket(getMessagesPacket(messages));
    }

    public void getPrivateChatMessages() throws IOException {
        String requester = connection.getUsername();
        String otherParty = receivedPacket.getAttribute().get("other party");
        ArrayList<Message> messages = ChatController.getPrivateChatMessages(requester, otherParty);
        connection.sendPacket(getMessagesPacket(messages));
    }

    public void getRoomMessages() throws IOException {
        String username = connection.getUsername();
        String roomID = receivedPacket.getAttribute().get("room id");
        ArrayList<Message> messages = ChatController.getRoomChatMessages(roomID, username);
        connection.sendPacket(getMessagesPacket(messages));
    }

    public void exitChat() {
        String username = connection.getUsername();
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat ID");
        ChatController.exitChat(username, chatType, chatID);
    }

    public void sendMessage() {
        String senderUsername = connection.getUsername();
        String messageBody = receivedPacket.getAttribute().get("message body");
        String timeSent = receivedPacket.getAttribute().get("time sent");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat id");
        ChatController.sendMessage(senderUsername, messageBody, timeSent, chatType, chatID);
    }

    public void isAdmin() throws IOException {
        String username = connection.getUsername();
        String roomID = receivedPacket.getAttribute().get("room id");
        boolean admin = ChatController.isAdmin(username, roomID);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("state", String.valueOf(admin));
        Packet toBeSent = new Packet(ServerToClientCommands.IS_ADMIN.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void addMemberToRoom() throws IOException {
        String roomID = receivedPacket.getAttribute().get("room id");
        String username = receivedPacket.getAttribute().get("username");
        ChatController.addMemberToRoom(roomID, username);
        Connection toBeInformed = ConnectionDatabase.getInstance().getConnectionByUsername(username);
        if (toBeInformed != null) {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("chat type", "room");
            attributes.put("room id", roomID);
            Packet toBeSent = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), attributes);
            toBeInformed.sendPacket(toBeSent);
        }
    }

    public void canUpdateMessage() throws IOException {
        String messageID = receivedPacket.getAttribute().get("message id");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat id");
        String username = connection.getUsername();
        boolean state = ChatController.canUpdateMessage(chatID, chatType, messageID, username);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("state", String.valueOf(state));
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_UPDATE_MESSAGE.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void deleteMessage() {
        String messageID = receivedPacket.getAttribute().get("message id");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat id");
        String username = connection.getUsername();
        ChatController.deleteMessage(messageID, chatType, chatID, username);
    }

    public void editMessage() {
        String messageID = receivedPacket.getAttribute().get("message id");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat id");
        String newBody = receivedPacket.getAttribute().get("new body");
        String username = connection.getUsername();
        ChatController.editMessage(messageID, chatType, chatID, newBody, username);
    }

    private Packet getMessagesPacket(ArrayList<Message> messages) {
        String messagesJson = new Gson().toJson(messages, new TypeToken<List<Message>>() {
        }.getType());
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("messages", messagesJson);
        return new Packet(ServerToClientCommands.GET_CHAT_MESSAGES.getCommand(), attributes);
    }

    private Packet getChatsPacket(ArrayList<String> myPrivateChats) throws IOException {
        String chatsJson = new Gson().toJson(myPrivateChats, new TypeToken<List<String>>() {
        }.getType());
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("chats", chatsJson);
        return new Packet(ServerToClientCommands.GET_CHAT_LIST.getCommand(), attributes);
    }
}