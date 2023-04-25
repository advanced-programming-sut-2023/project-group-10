package org.example.model.game;

import org.example.model.utils.RandomGenerator;

import java.util.HashMap;

public class Trade {
    private final String id;
    private final String recipientId;
    private final String senderId;
    private final HashMap<String, String> messages = new HashMap<>();
    private boolean isDisplayedInHistory;
    private final Item item;
    private final int amount;
    private final int price;
    private boolean acceptedStatus;


    public Trade(String recipientId, String senderId, String message, Item item, int amount, int price) {
        this.id = "trade" + RandomGenerator.tradeId();
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.messages.put(senderId, message);
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.acceptedStatus = false;
        this.isDisplayedInHistory = false;
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


    public static void tradeItem(Trade trade) {

    }

    public void setDisplayedInHistory(boolean displayedInHistory) {
        isDisplayedInHistory = displayedInHistory;
    }

    public boolean isDisplayedInHistory() {
        return isDisplayedInHistory;
    }
}