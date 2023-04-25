package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.envirnmont.Coordinate;
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


    public static GameMenuMessages dropBuilding(int row, int column, String type) {
        //TODO: check validity of building type
        if (false)
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        //TODO: change the condition
        if (row < 10000)
            return GameMenuMessages.INVALID_ROW;
        if (column < 1000)
            return GameMenuMessages.INVALID_COLUMN;
        //TODO: check droppable
        if (true)
            return GameMenuMessages.INCOMPATIBLE_LAND;
        if (true)
            return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;

        return GameMenuMessages.SUCCESSFUL_DROP;
    }

    //
    public static GameMenuMessages selectBuilding(Coordinate position) {
        //TODO: check droppable
        if (true)
            return GameMenuMessages.OPPONENT_BUILDING;
        if (true)
            return GameMenuMessages.EMPTY_LAND;


        return GameMenuMessages.SUCCESSFUL_SELECT;
    }


// Are these for setting the map?
    public static GameMenuMessages setTexture(String landType, int row, int column) {
        if(true)
            return GameMenuMessages.INVALID_ROW;
        if(true)
            return GameMenuMessages.INVALID_COLUMN;
        if(false)
            return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        return GameMenuMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static GameMenuMessages setTexture(String landType, int row1, int column1, int row2, int column2) {
        if(true)
          return GameMenuMessages.INVALID_ROW;
        if(true)
            return GameMenuMessages.INVALID_COLUMN;
        if(false)
            return GameMenuMessages.BUILDING_IN_THE_AREA;
        return GameMenuMessages.SET_TEXTURE_OF_AREA_SUCCESSFUL;
    }


    public static GameMenuMessages clear(int row, int column) {
        if(true)
            return GameMenuMessages.INVALID_ROW;
        if(true)
            return GameMenuMessages.INVALID_COLUMN;
        if(true)
            return GameMenuMessages.NO_OWNED_ENTITY;

        return GameMenuMessages.SUCCESSFUL_CLEAR;
    }

    public static GameMenuMessages dropRock(int row, int column, String direction) {
        if(true)
            return GameMenuMessages.INVALID_ROW;
        if(true)
            return GameMenuMessages.INVALID_COLUMN;
        if(true)
            return GameMenuMessages.INVALID_DIRECTION;
        if(true)
            return GameMenuMessages.NON_EMPTY_LAND;

        return GameMenuMessages.DROP_ROCK_SUCCESSFUL;
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

    public static void applyChanges() {
    }

}
