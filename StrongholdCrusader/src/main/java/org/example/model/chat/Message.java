package org.example.model.chat;

import org.example.model.User;

public class Message {
    private final String messageID;
    private final User sender;
    private final String timeSent;
    private String messageBody;
    // TODO: doubt
    private transient final Chat chat;

    public Message(User sender, String timeSent, String milliesSent, Chat chat, String messageBody) {
        messageID = sender.getUsername() + "_" + milliesSent;
        this.sender = sender;
        this.timeSent = timeSent;
        this.chat = chat;
        this.messageBody = messageBody;
    }

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

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Chat getChat() {
        return chat;
    }
}
