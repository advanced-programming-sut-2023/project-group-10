package org.example.view;

import org.example.controller.GameMenuController;
import org.example.model.User;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class GameMenu {
    public static void run() {

    }

    // Is importing OK?
    private static void showCurrentPlayer() {
        User player = GameMenuController.currentPlayer();
        System.out.println("player \" " + player.getNickname() + "\" with username : " + player.getUsername() + "is about to play!");
    }

    private static void showPopularityFactors() {
        System.out.println(GameMenuController.showPopularityFactors());
    }

    private static void showRoundsPlayed() {
        System.out.println(GameMenuController.roundsPlayed());

    }

    private static void showPlayers() {
        System.out.println(GameMenuController.showPlayers());
    }
    private static void showPopularity(){
        System.out.println(GameMenuController.showPopularity());
    }
    private static void showFoodList(){
        System.out.println(GameMenuController.showFoodList());
    }

    private static void setFoodRate(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String rate = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-r")) {
                rate = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }
        if(!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int foodRate=Integer.parseInt(rate);
        GameMenuMessages message=GameMenuController.setFoodRate(foodRate);
        switch (message){
            case INVALID_FOOD_RATE:
                System.out.println("Enter a number from -2 to 2!");
                break;
            case SET_FOOD_RATE_SUCCESSFUL:
                System.out.println("Food rate is set to "+foodRate);
        }

    }
    private static void showFoodRate(){
        System.out.println("Food rate is : "+GameMenuController.showFoodRate());
    }


    private static void setTaxRate(String  input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String rate = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-r")) {
                rate = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }
        if(!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int taxRate=Integer.parseInt(rate);
        GameMenuMessages message=GameMenuController.setFoodRate(taxRate);
        switch (message){
            case INVALID_TAX_RATE:
                System.out.println("Enter a number from -3 to 8!");
                break;
            case SET_FOOD_TAX_SUCCESSFUL:
                System.out.println("Food rate is set to "+taxRate);
        }
    }
    //TODO: do something to prevent duplicate code?
    private static void showTaxRate(){
        System.out.println("Tax rate is : "+GameMenuController.showTaxRate());
    }


    private static void showFearRate(){
        System.out.println("Fear rate is : "+GameMenuController.showFearRate());
    }


    private static void setFearRate(String  input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String rate = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-r")) {
                rate = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }
        if(!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int fearRate=Integer.parseInt(rate);
        GameMenuMessages message=GameMenuController.setFoodRate(fearRate);
        switch (message){
            case INVALID_TAX_RATE:
                System.out.println("Enter a number from -5 to 5!");
                break;
            case SET_FOOD_TAX_SUCCESSFUL:
                System.out.println("Food rate is set to "+fearRate);
        }
    }


    private static void dropBuilding(Matcher matcher) {


    }

    private static void selectBuilding(Matcher matcher) {

    }

    private static void createUnit(Matcher matcher) {


    }

    private static void repair() {

    }

    // UNIT RELATED
    private static void selectUnit(Matcher matcher) {

    }

    private static void setTexture(Matcher matcher) {

    }


    private static void clear(Matcher matcher) {


    }

    private static void dropRock(Matcher matcher) {

    }

    private static void dropTree(Matcher matcher) {

    }

    private static void endTurn() {

    }

    private static void leaveGame() {

    }

}
