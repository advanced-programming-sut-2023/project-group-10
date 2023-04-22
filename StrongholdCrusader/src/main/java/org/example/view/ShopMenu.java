package org.example.view;

import org.example.controller.ShopMenuController;
import org.example.view.enums.commands.ShopMenuCommands;
import org.example.view.enums.messages.ShopMenuMessages;


import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    public static void run(){
        Scanner scanner=new Scanner(System.in);
        Matcher matcher;
        while (true) {
            if ((matcher = ShopMenuCommands.getMatcher(scanner.nextLine(), ShopMenuCommands.SHOW_LIST)) != null)
                showPriceList();
            else if ((matcher = ShopMenuCommands.getMatcher(scanner.nextLine(),ShopMenuCommands.SELL)) != null)
                sell(matcher);
            else if ((matcher = ShopMenuCommands.getMatcher(scanner.nextLine(),ShopMenuCommands.BUY)) != null)
                buy(matcher);
            else if ((matcher = ShopMenuCommands.getMatcher(scanner.nextLine(),ShopMenuCommands.BACK)) != null){
                //TODO: go back to game menu
            }
            else
                System.out.println("Invalid command!");

        }
    }
    private static void showPriceList(){
        System.out.println(ShopMenuController.showPriceList());
    }
    private static void buy(Matcher matcher){
        String itemName = null;
        int amount=0;
        ShopMenuMessages message=ShopMenuController.buy(itemName,amount);
        switch (message){
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
    private static void sell(Matcher matcher){

        String itemName = null;
        int amount=0;
        ShopMenuMessages message=ShopMenuController.sell(itemName,amount);
        switch (message){
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
