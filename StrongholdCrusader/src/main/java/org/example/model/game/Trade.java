package org.example.model.game;

import org.example.model.utils.RandomGenerator;

import java.util.HashMap;
import java.util.Random;

public class Trade {
    private final String id;
    private final String recipientId;
    private final String senderId;
    private final HashMap<String, String> messages = new HashMap<>();
    //TODO: change this field after we completed items completely(buildings, entities, food and etc)
    private final String type;
    private final int amount;
    private final int price;
    private boolean acceptedStatus;



    public Trade(String recipientId, String senderId, String message, String type, int amount, int price) {
        this.id="trade"+ RandomGenerator.tradeId();
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.messages.put(senderId, message);
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.acceptedStatus = false;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage(String username) {
        return messages.get(username);
    }

    public void addMessage(String username, String message) {
            messages.put(username, message);
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(boolean acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }


    public static void tradeItem(Trade trade) {

    }

}