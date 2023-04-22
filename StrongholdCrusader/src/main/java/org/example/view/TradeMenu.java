package org.example.view;

import org.apache.commons.cli.*;
import org.example.controller.TradeMenuController;
import org.example.model.utils.ExceptionMessages;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.messages.TradeMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends Menu {
    public static void run() {


    }

    private static void sendRequest(Matcher matcher) {

        String arguments = matcher.replaceAll("");
        String[] args = InputProcessor.separateInput(arguments);
        Scanner scanner = new Scanner(System.in);

        Options options = new Options();
        Option typeOption = Option.builder().argName("t").longOpt("resourceType").hasArgs().required().desc("resourceType").build();
        Option amountOption = Option.builder().argName("a").longOpt("resourceAmount").hasArgs().required().desc("resourceAmount").build();
        Option priceOption = Option.builder().argName("p").longOpt("price").hasArgs().required().desc("price").build();
        Option messageOption = Option.builder().argName("m").longOpt("message").hasArgs().required().desc("message").build();
        Option recipientOption = Option.builder().argName("r").longOpt("recipientId").hasArgs().required().desc("recipientId").build();
        options.addOption(typeOption);
        options.addOption(amountOption);
        options.addOption(priceOption);
        options.addOption(messageOption);
        options.addOption(recipientOption);

        CommandLineParser parser = new DefaultParser(false);
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getOptionValues(typeOption).length != 1)
                throw new ParseException("error: resource type must have only one argument");
            String resourceType = cmd.getOptionValue(typeOption);

            if (cmd.getOptionValues(amountOption).length != 1)
                throw new ParseException("error: amount must have only one argument");
            int amount = Integer.parseInt(cmd.getOptionValue(amountOption));


            if (cmd.getOptionValues(priceOption).length != 1)
                throw new ParseException("error: price must have only one argument");
            int price = Integer.parseInt(cmd.getOptionValue(priceOption));


            if (cmd.getOptionValues(messageOption).length != 1)
                throw new ParseException("error: message must have only one argument");
            String message = cmd.getOptionValue(messageOption);


            if (cmd.getOptionValues(recipientOption).length != 1)
                throw new ParseException("error: recipient ID must have only one argument");
            String recipientId = cmd.getOptionValue(recipientOption);
            TradeMenuMessages tradeMessage = TradeMenuController.sendRequest(resourceType, amount, price, message, recipientId);
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
            }


        } catch (ParseException exception) {
            printMessage(ExceptionMessages.getMessageFromException(exception));
        }

    }

    private static void acceptRequest(Matcher matcher) {

    }

    private static void showHistory() {
        System.out.print(TradeMenuController.showHistory());
    }

    private static void showTradeList() {
        System.out.print(TradeMenuController.showTradeList());

    }

}
