package org.example.controller;

import org.example.model.User;
import org.example.model.chat.*;

import java.util.ArrayList;

public class ChatController {

    public static boolean canCreatePrivateChat(String requester, String otherParty) {
        return PrivateChat.getPrivateChatByMembers(requester, otherParty) == null && User.getUserByUsername(otherParty) != null;
    }

    public static void createPrivateChat(String requester, String otherParty) {
        PrivateChat.create(requester, otherParty);
    }

    public static boolean isRoomIDValid(String roomID) {
        return Room.getRoomById(roomID) == null;
    }

    public static void createRoom(String admin, String roomID) {
        Room.create(admin, roomID);
    }

    public static ArrayList<String> getMyPrivateChats(String username) {
        ArrayList<String> myPrivateChats = new ArrayList<>();
        ArrayList<PrivateChat> privateChats = PrivateChat.getAllPrivateChats();
        if (privateChats == null || privateChats.size() == 0)
            return null;
        for (PrivateChat privateChat : privateChats)
            if (privateChat.hasAccess(username)) myPrivateChats.add(privateChat.getOtherPartyUsername(username));
        return myPrivateChats;
    }

    public static ArrayList<String> getMyRooms(String username) {
        ArrayList<String> myRooms = new ArrayList<>();
        ArrayList<Room> allRooms = Room.getAllRooms();
        if (allRooms == null)
            return null;
        for (Room room : allRooms)
            if (room.hasAccess(username)) myRooms.add(room.getChatID());
        return myRooms;
    }

    public static ArrayList<Message> getPublicChatMessages(String username) {
        PublicChat publicChat = PublicChat.getInstance();
        if (!publicChat.getWatchersUsernames().contains(username)) publicChat.addWatcher(username);
        return publicChat.getMessages();
    }

    public static ArrayList<Message> getPrivateChatMessages(String requester, String otherParty) {
        PrivateChat privateChat = PrivateChat.getPrivateChatByMembers(requester, otherParty);
        if (privateChat == null) return null;
        if (!privateChat.getWatchersUsernames().contains(requester)) privateChat.addWatcher(requester);
        return privateChat.getMessages();
    }

    public static ArrayList<Message> getRoomChatMessages(String roomID, String username) {
        Room room = Room.getRoomById(roomID);
        if (room == null) return null;
        if (!room.getWatchersUsernames().contains(username)) room.addWatcher(username);
        return room.getMessages();
    }

    public static void exitChat(String username, String chatType, String chatID) {
        if (chatType.equals("public")) PublicChat.getInstance().exitChat(username);
        else if (chatType.equals("private")) PrivateChat.getPrivateChatByMembers(username, chatID).exitChat(username);
        else Room.getRoomById(chatID).exitChat(username);
    }

    public static void sendMessage(String senderUsername, String messageBody, String timeSent, String milliesSent, String chatType, String chatID) {
        Chat chat;
        if (chatType.equals("public")) chat = PublicChat.getInstance();
        else if (chatType.equals("private")) chat = PrivateChat.getPrivateChatByMembers(senderUsername, chatID);
        else chat = Room.getRoomById(chatID);
        chat.addMessage(new Message(User.getUserByUsername(senderUsername), timeSent, milliesSent, chat, messageBody));
    }

    public static boolean isAdmin(String username, String roomID) {
        return Room.getRoomById(roomID).isAdmin(username);
    }

    public static void addMemberToRoom(String roomID, String username) {
        Room room = Room.getRoomById(roomID);
        room.addToMembers(username);
        room.loadChatToDatabase();
    }

    public static boolean canUpdateMessage(String chatId, String chatType, String messageId, String username) {
        User sender;
        if (chatType.equals("public"))
            sender = PublicChat.getInstance().getMessageById(messageId).getSender();
        else if (chatType.equals("private"))
            sender = PrivateChat.getPrivateChatByMembers(username, chatId).getMessageById(messageId).getSender();
        else sender = Room.getRoomById(chatId).getMessageById(messageId).getSender();
        return sender.getUsername().equals(username);
    }

    public static void deleteMessage(String messageID, String chatType, String chatID, String username) {
        Chat chat;
        if (chatType.equals("public"))
            chat = PublicChat.getInstance();
        else if (chatType.equals("private"))
            chat = PrivateChat.getPrivateChatByMembers(username, chatID);
        else chat = Room.getRoomById(chatID);
        chat.deleteMessage(messageID);
    }

    public static void editMessage(String messageID, String chatType, String chatID, String newBody, String username) {
        Chat chat;
        if (chatType.equals("public"))
            chat = PublicChat.getInstance();
        else if (chatType.equals("private"))
            chat = PrivateChat.getPrivateChatByMembers(username, chatID);
        else chat = Room.getRoomById(chatID);
        chat.editMessage(messageID, newBody);
    }
}
