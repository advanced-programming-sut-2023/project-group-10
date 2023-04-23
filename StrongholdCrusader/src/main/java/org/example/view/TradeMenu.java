package org.example.view;

import org.example.controller.TradeMenuController;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.TradeMenuCommands;
import org.example.view.enums.messages.TradeMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TradeMenu extends Menu {
    public static void run() {
        //TODO: show notification for trades
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (TradeMenuCommands.getMatcher(input, TradeMenuCommands.SEND_REQUEST) != null) sendRequest(input);
            else if (TradeMenuCommands.getMatcher(input, TradeMenuCommands.ACCEPT) != null) acceptRequest(input);
            else if (TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_LIST) != null) showTradeList();
            else if (TradeMenuCommands.getMatcher(input, TradeMenuCommands.HISTORY) != null) showHistory();
            else if (TradeMenuCommands.getMatcher(input, TradeMenuCommands.BACK) != null) return;
            else System.out.println("Invalid command!");
        }
    }

    private static void sendRequest(String input) {
        // process input
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String resourceType = "";
        String resourceAmountString = "";
        int resourceAmount = 0;
        String price = "";
        String message = "";
        String recipientId = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-t":
                    resourceType = option.getValue();
                    break;
                case "-a":
                    resourceAmountString = option.getValue();
                    break;
                case "-p":
                    price = option.getValue();
                    break;
                case "-m":
                    message = option.getValue();
                    break;
                case "-r":
                    recipientId = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
            }
        }
        if (resourceType == null | resourceAmountString == null || price == null || message == null || recipientId == null) {
            System.out.println("empty field");
            return;
        }
        if (resourceType.isEmpty() || resourceAmountString.isEmpty() || price.isEmpty() || message.isEmpty() || recipientId.isEmpty()) {
            System.out.println("missing argument");
            return;
        }
        if (!resourceAmountString.matches("\\d+")) {
            System.out.println("invalid amount");
            return;
        } else resourceAmount = Integer.parseInt(resourceAmountString);

        TradeMenuMessages tradeMessage = TradeMenuController.sendRequest(resourceType, resourceAmount, price, message, recipientId);
        switch (tradeMessage) {
            case INSUFFICIENT_STOCK:
                System.out.println("You don't have enough resources to donate!");
                break;
            case INVALID_USER:
                System.out.println("There is no user with this id in the current battle!");
                break;
            case INVALID_TYPE:
                System.out.println("There is no resource with this type");
                break;
            case TRADE_ADDED_TO_TRADELIST:
                System.out.println("trade was added successfully");
                break;
        }
    }

    private static void acceptRequest(String input) {

    }

    private static void showHistory() {
        System.out.print(TradeMenuController.showHistory());
    }

    private static void showTradeList() {
        System.out.print(TradeMenuController.showTradeList());
    }
}
