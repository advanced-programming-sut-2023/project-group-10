package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Map;

public class GameMenuController {
    public static User currentPlayer() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
    }

    public static String showPopularityFactors() {
        String show = "";
        HashMap<String, Integer> popularityFactors = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularityFactors();
        for (Map.Entry<String, Integer> factors : popularityFactors.entrySet()) {
            show = show.concat(factors.getKey() + " : " + factors.getValue() + "\n");
        }
        return show;
    }

    public static int roundsPlayed() {

        return Stronghold.getCurrentBattle().getTurnsPassed();
    }

    public static String showPlayers() {
        String players = "";
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            players = players.concat("player: " + government.getOwner().getNickname() + "is playing (username: " + government.getOwner().getUsername() + " )\n");
        }
        return players;
    }

    public static int showPopularity() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity();
    }

    public static String showFoodList() {
        String foodList = "";

        for (Map.Entry<Item, Integer> list : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodList().entrySet()) {
            foodList = foodList.concat("Food : " + list.getKey() + " Amount : " + list.getValue() + "\n");
        }
        return foodList;
    }

    //ATTENTION: I gave the modified rate to gov
    public static GameMenuMessages setFoodRate(int foodRate) {
        if (foodRate < -2 || foodRate > 2)
            return GameMenuMessages.INVALID_FOOD_RATE;
        //TODO: feed people by foodRate+2
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFoodRate(foodRate);
        //TODO: affect it on popularity!
        return GameMenuMessages.SET_FOOD_RATE_SUCCESSFUL;

    }

    public static GameMenuMessages setTaxRate(int taxRate) {
        if (taxRate < -3 || taxRate > 8)
            return GameMenuMessages.INVALID_TAX_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(taxRate);
        return GameMenuMessages.SET_FOOD_TAX_SUCCESSFUL;

    }

    public static GameMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || fearRate > 5)
            return GameMenuMessages.INVALID_TAX_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(fearRate);
        return GameMenuMessages.SET_FOOD_TAX_SUCCESSFUL;
    }


    public static int showTaxRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate();
    }

    public static int showFoodRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate();
    }

    public static int showFearRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate();
    }


    public static void chooseMapSize(int length) {

    }


    public static void applyChanges() {
    }


    public static GameMenuMessages dropBuilding(int row, int column, String type) {
        return null;
    }

    //
    public static GameMenuMessages selectBuilding(int row, int column) {
        return null;
    }

    // what does "drop unit" do if we already have " create unit"
    public static GameMenuMessages createUnit(String type, int count) {
        return null;
    }

    public GameMenuMessages repair() {
        return null;
    }

    // UNIT RELATED
    public static GameMenuMessages selectUnit(int row, int column) {
        return null;
    }


    public static GameMenuMessages setTexture(String landType, int row, int column) {
        return null;
    }

    public static GameMenuMessages setTexture(String landType, int row1, int column1, int row2, int column2) {
        return null;
    }


    public static GameMenuMessages clear(int row, int column) {
        return null;
    }

    public static GameMenuMessages dropRock(int row, int column, String direction) {
        return null;
    }

    public static GameMenuMessages dropTree(int row, int column, String type) {
        return null;
    }

    public static GameMenuMessages endTurn() {
        return null;
    }

    public static GameMenuMessages leaveGame() {
        return null;
    }
}
