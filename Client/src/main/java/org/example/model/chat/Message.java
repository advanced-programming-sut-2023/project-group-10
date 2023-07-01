package org.example.model.chat;

import org.example.model.User;

public class Message {
    private String messageID;
    private User sender;
    private String timeSent;
    private String messageBody;


    public String getMessageID() {
        return messageID;
    }

    public User getSender() {
        return sender;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
