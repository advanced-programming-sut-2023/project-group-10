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
import java.util.Map;

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
        String requester = receivedPacket.getAttribute().get("requester");
        String otherParty = receivedPacket.getAttribute().get("other party");
        boolean state = ChatController.canCreatePrivateChat(requester, otherParty);
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_CREATE_CHAT.getCommand(), (HashMap<String, String>) Map.of("state", String.valueOf(state)));
        connection.sendPacket(toBeSent);
    }

    public void createPrivateChat() throws IOException {
        String requester = receivedPacket.getAttribute().get("requester");
        String otherParty = receivedPacket.getAttribute().get("other party");
        ChatController.createPrivateChat(requester, otherParty);
        Packet toBeSentToRequester = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), (HashMap<String, String>) Map.of("chat type", "private", "chat id", otherParty));
        connection.sendPacket(toBeSentToRequester);
        Connection otherConnection = ConnectionDatabase.getInstance().getConnectionByUsername(otherParty);
        if (otherConnection != null) {
            Packet toBeSentToOtherParty = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), (HashMap<String, String>) Map.of("chat type", "private", "chat id", requester));
            otherConnection.sendPacket(toBeSentToOtherParty);
        }
    }

    public void isRoomIDValid() throws IOException {
        String roomID = receivedPacket.getAttribute().get("room id");
        boolean state = ChatController.isRoomIDValid(roomID);
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_CREATE_CHAT.getCommand(), (HashMap<String, String>) Map.of("state", String.valueOf(state)));
        connection.sendPacket(toBeSent);
    }

    public void creatRoom() throws IOException {
        String admin = receivedPacket.getAttribute().get("admin");
        String roomID = receivedPacket.getAttribute().get("room id");
        ChatController.createRoom(admin, roomID);
        Packet toBeSent = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), (HashMap<String, String>) Map.of("chat type", "room", "room id", roomID));
        connection.sendPacket(toBeSent);
    }

    public void getMyPrivateChats() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        ArrayList<String> myPrivateChats = ChatController.getMyPrivateChats(username);
        String chatsJson = new Gson().toJson(myPrivateChats, new TypeToken<List<String>>() {
        }.getType());
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CHAT_LIST.getCommand(), (HashMap<String, String>) Map.of("chats", chatsJson));
        connection.sendPacket(toBeSent);
    }

    public void getMyRooms() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        ArrayList<String> myRooms = ChatController.getMyRooms(username);
        String chatsJson = new Gson().toJson(myRooms, new TypeToken<List<String>>() {
        }.getType());
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CHAT_LIST.getCommand(), (HashMap<String, String>) Map.of("chats", chatsJson));
        connection.sendPacket(toBeSent);
    }

    public void getPublicChatMessages() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        ArrayList<Message> messages = ChatController.getPublicChatMessages(username);
        String messagesJson = messageArraylistToJson(messages);
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CHAT_MESSAGES.getCommand(), (HashMap<String, String>) Map.of("messages", messagesJson));
        connection.sendPacket(toBeSent);
    }

    public void getPrivateChatMessages() throws IOException {
        String requester = receivedPacket.getAttribute().get("requester");
        String otherParty = receivedPacket.getAttribute().get("other party");
        ArrayList<Message> messages = ChatController.getPrivateChatMessages(requester, otherParty);
        String messagesJson = messageArraylistToJson(messages);
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CHAT_MESSAGES.getCommand(), (HashMap<String, String>) Map.of("messages", messagesJson));
        connection.sendPacket(toBeSent);
    }

    public void getRoomMessages() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        String roomID = receivedPacket.getAttribute().get("room id");
        ArrayList<Message> messages = ChatController.getRoomChatMessages(roomID, username);
        String messagesJson = messageArraylistToJson(messages);
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CHAT_MESSAGES.getCommand(), (HashMap<String, String>) Map.of("messages", messagesJson));
        connection.sendPacket(toBeSent);
    }

    private String messageArraylistToJson(ArrayList<Message> messages) {
        return new Gson().toJson(messages, new TypeToken<List<Message>>() {
        }.getType());
    }

    public void exitChat() {
        String username = receivedPacket.getAttribute().get("username");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat ID");
        ChatController.exitChat(username, chatType, chatID);
    }

    public void sendMessage() {
        String senderUsername = receivedPacket.getAttribute().get("sender username");
        String messageBody = receivedPacket.getAttribute().get("message body");
        String timeSent = receivedPacket.getAttribute().get("time sent");
        String chatType = receivedPacket.getAttribute().get("chat type");
        String chatID = receivedPacket.getAttribute().get("chat id");
        ChatController.sendMessage(senderUsername, messageBody, timeSent, chatType, chatID);
    }

    public void isAdmin() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        String roomID = receivedPacket.getAttribute().get("room id");
        boolean admin = ChatController.isAdmin(username, roomID);
        Packet toBeSent = new Packet(ServerToClientCommands.IS_ADMIN.getCommand(), (HashMap<String, String>) Map.of("state", String.valueOf(admin)));
        connection.sendPacket(toBeSent);
    }

    public void addMemberToRoom() throws IOException {
        String roomID = receivedPacket.getAttribute().get("room id");
        String username = receivedPacket.getAttribute().get("username");
        ChatController.addMemberToRoom(roomID, username);
        Connection toBeInformed = ConnectionDatabase.getInstance().getConnectionByUsername(username);
        if (toBeInformed != null) {
            Packet toBeSent = new Packet(ServerToClientCommands.NEW_CHAT_ADDED.getCommand(), (HashMap<String, String>) Map.of("chat type", "room", "room id", roomID));
            toBeInformed.sendPacket(toBeSent);
        }
    }
}