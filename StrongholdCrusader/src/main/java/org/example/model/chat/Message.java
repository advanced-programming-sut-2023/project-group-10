package org.example.model.chat;

import org.example.model.User;

public class Message {
    private final User sender;
    private final String timeSent;
    private String message;
    // TODO: doubt
    private final Chat chat;

    public Message(User sender, String timeSent, Chat chat) {
        this.sender = sender;
        this.timeSent = timeSent;
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }
}
