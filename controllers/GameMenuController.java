package main.java.controller;

import main.java.model.User;
import main.java.model.environment.Map;
import main.java.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {

    public int roundsPlayed() {
        return 0;
    }

    public User currentPlayer() {
        return null;
    }

    // not sure
    public void applyChanges() {
    }


    public HashMap<String, Boolean> showPopularityFactors() {
        return null;
    }

    public static void setFoodRate(Matcher matcher) {

    }

    public static GameMenuMessages setTaxRate(Matcher matcher) {
        return null;
    }

    public static GameMenuMessages setFearRate(Matcher matcher) {
        return null;
    }

    public User[] getPlayers() {
        return null;
    }

    public static Map showMap(Matcher matcher) {
        return null;
    }


    public static GameMenuMessages dropBuilding(Matcher matcher) {
        return null;
    }

    //
    public static GameMenuMessages selectBuilding(Matcher matcher) {
        return null;
    }

    // what does "drop unit" do if we already have " create unit"
    public static GameMenuMessages createUnit(Matcher matcher) {
        return null;
    }

    public GameMenuMessages repair() {
        return null;
    }

    // UNIT RELATED
    public static GameMenuMessages selectUnit(Matcher matcher) {
        return null;
    }


    public static GameMenuMessages setTextureProcessor(Matcher matcher) {
        return null;

    }

    public static GameMenuMessages setTexture(String landType, int row, int column) {
        return null;
    }

    public static GameMenuMessages setTexture(String landType, int row1, int colum1, int row2, int column2n) {
        return null;
    }


    public static GameMenuMessages clear(Matcher matcher) {
        return null;
    }

    public static GameMenuMessages dropRock(Matcher matcher) {
        return null;
    }

    public static GameMenuMessages dropTree(Matcher matcher) {
        return null;
    }


}
