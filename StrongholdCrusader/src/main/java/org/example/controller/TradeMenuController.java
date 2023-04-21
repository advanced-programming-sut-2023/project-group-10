package org.example.controller;


import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Trade;
import org.example.view.enums.messages.TradeMenuMessages;

public class TradeMenuController {
    // show users in view


    //TODO: add remove entity in government after Yasna uploaded them
    public static TradeMenuMessages sendRequest(String resourceType, int resourceAmount, int price, String message, String recipientId) {
        if (Stronghold.getCurrentBattle().getGovernmentByOwnerId(recipientId) == null)
            return TradeMenuMessages.INVALID_USER;
        //TODO: should I add anything to check if there is any items of such?
        if (price == 0 && Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername())
                .getItemFromListByType(resourceType) < resourceAmount)
            return TradeMenuMessages.INSUFFICIENT_STOCK;
        String senderId = Stronghold.getCurrentUser().getUsername();
        if (price == 0) {
            String tmp = recipientId;
            recipientId = senderId;
            senderId = tmp;
        }
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername()).addToTradeList(
                new Trade(recipientId, senderId, message, resourceType, resourceAmount, price));
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(recipientId).addToTradeList(
                new Trade(recipientId, senderId, message, resourceType, resourceAmount, price));
        return TradeMenuMessages.TRADE_ADDED_TO_TRADELIST;
    }

    public static String showTradeList() {
        //TODO:
        return null;
    }


    public static String showHistory() {


        return null;
    }

    public static TradeMenuMessages acceptRequest(String id, String message) {
        if (Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id) == null)
            return TradeMenuMessages.INVALID_TRADE_ID;

        Government recipient = Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentBattle().getGovernmentByOwnerId
                        (Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id).getRecipientId());

        Government sender = Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentBattle().getGovernmentByOwnerId
                        (Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id).getSenderId());
        if (!recipient.getOwner().getUsername().equals(Stronghold.getCurrentUser().getUsername()))
            return TradeMenuMessages.MISMATCH_OF_TRADERS;
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername())
                .getTradeFromTradeList(id).setAcceptedStatus(true);
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id)
                .addMessage(Stronghold.getCurrentUser().getUsername(), message);

        recipient.addItem(Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id));
        sender.reduceItem(Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id));
        return TradeMenuMessages.TRADE_SUCCESSFULLY_ACCEPTED;
    }


}
