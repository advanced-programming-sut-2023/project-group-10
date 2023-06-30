package org.example.model.chat;

import org.example.model.User;

import java.util.ArrayList;

public abstract class Chat {
    private final String chatID;
    private final ArrayList<Message> messages;
    private final ArrayList<String> watchersUsernames;

    public Chat(String chatID) {
        this.chatID = chatID;
        messages = new ArrayList<>();
        watchersUsernames = new ArrayList<>();
    }

    public String getChatID() {
        return chatID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message newMessage) {
        messages.add(newMessage);
        informWatchers();
    }

    public void addWatcher(String username) {
        watchersUsernames.add(username);
    }

    // TODO: move to controller/handler
    public void informWatchers() {
    }

    public abstract boolean hasAccess(String username);
}
