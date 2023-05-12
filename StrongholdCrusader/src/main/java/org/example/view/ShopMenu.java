package org.example.view;

import org.example.controller.ShopMenuController;
import org.example.model.Stronghold;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.ShopMenuCommands;
import org.example.view.enums.messages.ShopMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if ((ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_LIST)) != null) showPriceList();
            else if(ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_GOLD) != null)
                System.out.println(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getGold());
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.SELL) != null) sell(input);
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.BUY) != null) buy(input);
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.BACK) != null) return;
            else if (input.matches("^\\s*show\\s+menu\\s+name\\s*$")) System.out.println("shop menu");
            else System.out.println("Invalid command!");

        }
    }

    private static void showPriceList() {
        System.out.println(ShopMenuController.showPriceList());
    }

    private static Map.Entry<String, Integer> getItemNameAndAmount(HashMap<String, String> options) throws Exception {
        String itemName = "";
        String itemAmount = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if ("-i".equals(option.getKey())) itemName = option.getValue();
            else if ("-a".equals(option.getKey())) itemAmount = option.getValue();
            else throw new Exception("invalid option");
        }
        if (!itemAmount.matches("-?\\d+")) throw new Exception("enter a number for amount");
        return Map.entry(itemName, Integer.parseInt(itemAmount));
    }

    private static void buy(String input) {
        try {
            Map.Entry<String, Integer> itemProperties = getItemNameAndAmount(InputProcessor.separateInput(input));
            ShopMenuMessages message = ShopMenuController.buy(itemProperties.getKey(), itemProperties.getValue());
            switch (message) {
                case INVALID_ITEM:
                    System.out.println("There is no such item in the shop!");
                    break;
                case INVALID_AMOUNT:
                    System.out.println("You must enter a number greater than 0!");
                    break;
                case INSUFFICIENT_GOLD:
                    System.out.println("You don't have enough gold to buy this item!");
                    break;
                case SUCCESS:
                    System.out.println("you've successfully purchased the item!");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void sell(String input) {
        try {
            Map.Entry<String, Integer> itemProperties = getItemNameAndAmount(InputProcessor.separateInput(input));
            ShopMenuMessages message = ShopMenuController.sell(itemProperties.getKey(), itemProperties.getValue());
            switch (message) {
                case INVALID_ITEM:
                    System.out.println("There is no such item!");
                    break;
                case INVALID_AMOUNT:
                    System.out.println("You must enter a number greater than 0!");
                    break;
                case INSUFFICIENT_AMOUNT:
                    System.out.println("You don't have enough storage of this item!");
                    break;
                case SUCCESS:
                    System.out.println("you've successfully sold the item!");
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
