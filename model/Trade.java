package main.java.model;

import java.util.ArrayList;

public class Trade {
    private final String recipientId;
    private final String senderId;
    private final String message;
    //TODO: change this field after we completed items completely(buildings, entities, food and etc)
    private final Item item;
    private final int amount;
    private final int price;
    private boolean acceptedStatus;


    public Trade(String recipientId, String senderId, String message, String type, int amount, int price) {
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.message = message;
        this.item=Item.valueOf(type);
        this.amount = amount;
        this.price = price;
        this.acceptedStatus = false;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public Item getItem() {
        return item;
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
}