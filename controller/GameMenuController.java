package main.java.controller;

import main.java.model.User;
import main.java.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.regex.Matcher;

public class GameMenuController {
    public void chooseMapSize(int length) {

    }


    public int roundsPlayed() {
        return 0;
    }

    public User currentPlayer() {
        return null;
    }

    public void applyChanges() {
    }


    public HashMap<String, Integer> showPopularityFactors() {
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
    public static GameMenuMessages goToNextTurn(){
        return null;
    }
    public static GameMenuMessages goToNextPlayer(){
        return null;
    }
    public static GameMenuMessages leaveGame(){
        return null;
    }


}
