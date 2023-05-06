package org.example.view;

import org.example.controller.ShopMenuController;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.ShopMenuCommands;
import org.example.view.enums.messages.ShopMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (true) {
            if ((ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_LIST)) != null)
                showPriceList();
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.SELL) != null)
                sell(input);
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.BUY) != null)
                buy(input);
            else if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.BACK) != null) {
                //TODO: go back to game menu
            }
            else if(input.matches("^\\s*show\\s+menu\\s+name\\s*$"))
                System.out.println("shop menu");
            else
                System.out.println("Invalid command!");

        }
    }

    private static void showPriceList() {
        System.out.println(ShopMenuController.showPriceList());
    }

    private static void buy(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String itemName = "";
        String itemAmount = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-i":
                    itemName = option.getValue();
                    break;
                case "-a":
                    itemAmount = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
                    return;
            }
        }
        int amount = Integer.parseInt(itemAmount);
        ShopMenuMessages message = ShopMenuController.buy(itemName, amount);
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

    }

    private static void sell(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String itemName = "";
        String itemAmount = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-i":
                    itemName = option.getValue();
                    break;
                case "-a":
                    itemAmount = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
                    return;
            }
        }
        int amount = Integer.parseInt(itemAmount);
        ShopMenuMessages message = ShopMenuController.sell(itemName, amount);
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

    }
}
