package controller;

import model.User;
import view.enums.messages.GameMenuMessages;

public class GameMenuController {
    public static void chooseMapSize(int length) {

    }

    public static int roundsPlayed() {
        return 0;
    }

    public static User currentPlayer() {
        return null;
    }

    public static void applyChanges() {
    }


    public static String showPopularityFactors() {
        return null;
    }

    public static void setFoodRate(int foodRate) {

    }

    public static GameMenuMessages setTaxRate(int taxRate) {
        return null;
    }

    public static GameMenuMessages setFearRate(int fearRate) {
        return null;
    }

    public User[] getPlayers() {
        return null;
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

    public static GameMenuMessages dropRock(int row, int column,String direction) {
        return null;
    }

    public static GameMenuMessages dropTree(int row, int column,String type) {
        return null;
    }

    public static GameMenuMessages endTurn(){
        return null;
    }

    public static GameMenuMessages leaveGame(){
        return null;
    }
}