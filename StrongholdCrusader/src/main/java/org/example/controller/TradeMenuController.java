package org.example.controller;


import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Trade;
import org.example.view.enums.messages.TradeMenuMessages;

public class TradeMenuController {
    public String showAllUsers() {
        String usersOfTheGame = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getNickname() + "(username : " +
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername() + ") : YOU\n";
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (!government.getOwner().getUsername().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
                usersOfTheGame = usersOfTheGame.concat(government.getOwner().getNickname() + "(username : " + government.getOwner().getUsername() + ")\n");
        }
        //remember to use .print not .println
        return usersOfTheGame;
    }


    //TODO: add remove entity in government after Yasna uploaded them
    public static TradeMenuMessages sendRequest(String resourceType, int resourceAmount, int price, String message, String recipientId) {
        //TODO: handle if the type was incorrect
        if(false)
            return TradeMenuMessages.INVALID_TYPE;
        if (Stronghold.getCurrentBattle().getGovernmentByOwnerId(recipientId) == null)
            return TradeMenuMessages.INVALID_USER;
        //TODO: should I add anything to check if there is any items of such?
        if (price == 0 && Stronghold.getCurrentBattle().getGovernmentByOwnerId(
                        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())
                .getItemFromListByType(resourceType) < resourceAmount)
            return TradeMenuMessages.INSUFFICIENT_STOCK;
        String senderId = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername();
        if (price == 0) {
            String tmp = recipientId;
            recipientId = senderId;
            senderId = tmp;
        }
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentBattle().
                getGovernmentAboutToPlay().getOwner().getUsername()).addToTradeList(
                new Trade(recipientId, senderId, message, resourceType, resourceAmount, price));
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(recipientId).addToTradeList(
                new Trade(recipientId, senderId, message, resourceType, resourceAmount, price));
        return TradeMenuMessages.TRADE_ADDED_TO_TRADELIST;
    }

    public static String showTradeList() {
        String tradeList="Accepted:\n";
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if(trade.getRecipientId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())
            && trade.isAcceptedStatus())
                tradeList=tradeList.concat("user [ "+trade.getSenderId()+" ] sent you -- "+trade.getAmount()+ " -- of { "
                + trade.getType()+" } for price: "+trade.getPrice()+"\n");
        }
        tradeList=tradeList.concat("Sent:\n");
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if(trade.getSenderId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
                tradeList=tradeList.concat("You've sent trade:[ "+trade.getId()+" ] request to user :"+
                        trade.getRecipientId()+" , you suggested "+trade.getPrice()+" golds for ( "+ trade.getAmount() +" )  of type: "+
                        trade.getType()+"\n");
        }

        return tradeList;
    }


    public static String showHistory() {
        String history="";
        for (Trade trade : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList()) {
            if(trade.getRecipientId().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())
                    && !trade.isDisplayedInHistory()) {
                history=history.concat("TradeId: "+trade.getId()+"from user: "+trade.getSenderId()+"; "+trade.getAmount()+
                        " item/items of type: "+ trade.getType()+" for price of :"+trade.getPrice()+"\n");
            }
        }

        return history;
    }

    public static TradeMenuMessages acceptRequest(String id, String message) {
        Trade trade = Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id);
        if (trade == null)
            return TradeMenuMessages.INVALID_TRADE_ID;
        Government recipient = Stronghold.getCurrentBattle().getGovernmentByOwnerId
                (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername());
        Government sender = Stronghold.getCurrentBattle().getGovernmentByOwnerId(trade.getSenderId());
        if (!recipient.getOwner().getUsername().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
            return TradeMenuMessages.MISMATCH_OF_TRADERS;
        if (trade.getPrice() != 0 && Stronghold.getCurrentBattle().getGovernmentByOwnerId(trade.getRecipientId()).getGold() < trade.getPrice() * trade.getAmount())
            return TradeMenuMessages.NOT_SUFFICIENT_GOLD;
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername())
                .getTradeFromTradeList(id).setAcceptedStatus(true);
        Stronghold.getCurrentBattle().getGovernmentByOwnerId(Stronghold.getCurrentUser().getUsername()).getTradeFromTradeList(id)
                .addMessage(Stronghold.getCurrentUser().getUsername(), message);
        //does it work?
        recipient.addItem(trade);
        sender.reduceItem(trade);
        return TradeMenuMessages.TRADE_SUCCESSFULLY_ACCEPTED;
    }


}
