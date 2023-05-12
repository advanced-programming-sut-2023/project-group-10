package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Item;
import org.example.view.enums.messages.ShopMenuMessages;

public class ShopMenuController {

    public static String showPriceList() {
        String list = "~~~ PRICE LIST ~~~";
        for (Item item : Item.values()) {
            list += "\nname: " + item.getName();
            list += "\nprice: " + item.getBuyPrice();
            list += "\nyou have " + Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getItemCount(item) + " of this item";
        }
        return list;
    }

    public static ShopMenuMessages buy(String name, int amount) {
        if (Item.getItemByName(name) == null)
            return ShopMenuMessages.INVALID_ITEM;
        if (amount <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getGold() < Item.getItemByName(name).getBuyPrice() * amount)
            return ShopMenuMessages.INSUFFICIENT_GOLD;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().addItem(Item.getItemByName(name), amount);
        return ShopMenuMessages.SUCCESS;
    }

    public static ShopMenuMessages sell(String name, int amount) {
        if (Item.getItemByName(name) == null)
            return ShopMenuMessages.INVALID_ITEM;
        if (amount <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getItemCount(Item.getItemByName(name)) < amount)
            return ShopMenuMessages.INSUFFICIENT_AMOUNT;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().reduceItem(Item.getItemByName(name), amount, Item.getItemByName(name).getSellPrice());
        return ShopMenuMessages.SUCCESS;
    }
}
