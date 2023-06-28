package org.example.controller;


import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.Trade;
import org.example.view.enums.messages.TradeMenuMessages;

public class TradeMenuController {
    public static String showAllUsers() {
        String usersOfTheGame = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getNickname() + "(username : " +
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername() + ") : YOU\n";
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government != Stronghold.getCurrentBattle().getGovernmentAboutToPlay())
                usersOfTheGame = usersOfTheGame.concat(government.getOwner().getNickname() + "(username : " + government.getOwner().getUsername() + ")\n");
        }
        return usersOfTheGame;
    }

    public static TradeMenuMessages sendRequest(String resourceType, int resourceAmount, int price, String message, String recipientId) {
        Item resource = Item.getItemByName(resourceType);
        if (resource == null)
            return TradeMenuMessages.INVALID_TYPE;

        String senderId = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername();
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().addToTradeList(
                new Trade(recipientId, senderId, message, resource, resourceAmount, price));
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(recipientId).addToTradeList(
                new Trade(recipientId, senderId, message, resource, resourceAmount, price));
        return TradeMenuMessages.TRADE_ADDED_TO_TRADELIST;
    }

    public static String showTradeList() {
        String tradeList = "Accepted:\n";
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if (trade.getRecipientId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())
                    && trade.isAcceptedStatus())
                tradeList = tradeList.concat("user [ " + trade.getSenderId() + " ] sent you -- " + trade.getAmount() + " -- of { "
                        + trade.getItem() + " } for price: " + trade.getPrice() + "\n");
        }
        tradeList = tradeList.concat("Sent:\n");
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if (trade.getSenderId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
                tradeList = tradeList.concat("You've sent trade:[ " + trade.getId() + " ] request to user :" +
                        trade.getRecipientId() + " , you suggested " + trade.getPrice() + " golds for ( " + trade.getAmount() + " )  of type: " +
                        trade.getItem() + "\n");
        }

        return tradeList;
    }


    public static String showHistory() {
        String history = "";
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if (trade.getRecipientId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())
                    && !trade.isDisplayedInHistory()) {
                history = history.concat("TradeId: " + trade.getId() + "from user: " + trade.getSenderId() + "; " + trade.getAmount() +
                        " item/items of type: " + trade.getItem() + " for price of :" + trade.getPrice() + "\n");
            }
        }

        return history;
    }

    public static TradeMenuMessages acceptRequest(Trade trade) {


        Government recipient = Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername());
        System.out.println(trade.getSenderId());
        Government sender = Stronghold.getCurrentBattle().getGovernmentByOwnerId(trade.getSenderId());
        if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getStorageSpaceForItem(trade.getItem()) <= 0)
            return TradeMenuMessages.NOT_ENOUGH_SPACE;
        if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getGold() < trade.getPrice() * trade.getAmount())
            return TradeMenuMessages.NOT_SUFFICIENT_GOLD;
        trade.setAcceptedStatus(true);
        recipient.reduceItem(trade);
        sender.addItem(trade);
        return TradeMenuMessages.TRADE_SUCCESSFULLY_ACCEPTED;
    }


}
