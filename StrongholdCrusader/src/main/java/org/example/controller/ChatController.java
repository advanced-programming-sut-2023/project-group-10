package org.example.controller;

import org.example.model.chat.*;

import java.util.ArrayList;

public class ChatController {
    public static ArrayList<Message> enterChat(String chatID, String username) {
        Chat chat = getChatByID(chatID);
        // TODO: handle chat not found separately from chat not accessible if needed
        if (chat == null) return null;
        if (!chat.hasAccess(username)) return null;
        chat.addWatcher(username);
        return chat.getMessages();
    }

    public static Chat getChatByID(String chatID) {
        PublicChat publicChatInstance = PublicChat.getInstance();
        if (chatID.equals(publicChatInstance.getChatID())) return publicChatInstance;
        ArrayList<PrivateChat> privateChats = PrivateChat.getAllPrivateChats();
        for (PrivateChat privateChat : privateChats)
            if (privateChat.getChatID().equals(chatID)) return privateChat;
        ArrayList<Room> rooms = Room.getAllRooms();
        for (Room room : rooms)
            if (room.getChatID().equals(chatID)) return room;
        return null;
    }
}
