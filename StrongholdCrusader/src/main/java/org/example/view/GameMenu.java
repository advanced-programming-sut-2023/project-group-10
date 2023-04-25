package org.example.view;

import org.example.controller.GameMenuController;
import org.example.model.User;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Map;

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

    private static void showPopularity() {
        System.out.println(GameMenuController.showPopularity());
    }

    private static void showFoodList() {
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
        if (!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int foodRate = Integer.parseInt(rate);
        GameMenuMessages message = GameMenuController.setFoodRate(foodRate);
        switch (message) {
            case INVALID_FOOD_RATE:
                System.out.println("Enter a number from -2 to 2!");
                break;
            case SET_FOOD_RATE_SUCCESSFUL:
                System.out.println("Food rate is set to " + foodRate);
        }

    }

    private static void showFoodRate() {
        System.out.println("Food rate is : " + GameMenuController.showFoodRate());
    }


    private static void setTaxRate(String input) {
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
        if (!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int taxRate = Integer.parseInt(rate);
        GameMenuMessages message = GameMenuController.setFoodRate(taxRate);
        switch (message) {
            case INVALID_TAX_RATE:
                System.out.println("Enter a number from -3 to 8!");
                break;
            case SET_FOOD_TAX_SUCCESSFUL:
                System.out.println("Food rate is set to " + taxRate);
        }
    }

    //TODO: do something to prevent duplicate code?
    private static void showTaxRate() {
        System.out.println("Tax rate is : " + GameMenuController.showTaxRate());
    }


    private static void showFearRate() {
        System.out.println("Fear rate is : " + GameMenuController.showFearRate());
    }


    private static void setFearRate(String input) {
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
        if (!rate.matches("-?\\d+"))
            System.out.println("You should enter a number for rate!");
        int fearRate = Integer.parseInt(rate);
        GameMenuMessages message = GameMenuController.setFoodRate(fearRate);
        switch (message) {
            case INVALID_TAX_RATE:
                System.out.println("Enter a number from -5 to 5!");
                break;
            case SET_FOOD_TAX_SUCCESSFUL:
                System.out.println("Food rate is set to " + fearRate);
        }
    }

    private static void dropBuilding(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = "";
        String x = "";
        String y = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-t":
                    type = option.getValue();
                    break;
                case "-x":
                    x = option.getValue();
                    break;
                case "-y":
                    y = option.getValue();
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!x.matches("-?\\d+"))
            System.out.println("You should enter a number for row!");

        if (!y.matches("-?\\d+"))
            System.out.println("You should enter a number for column!");
        int row = Integer.parseInt(x);
        int column = Integer.parseInt(y);
        GameMenuMessages message = GameMenuController.dropBuilding(row, column, type);
        switch (message) {
            case INVALID_BUILDING_TYPE:
                System.out.println("You've entered invalid building type!");
                break;
            case INVALID_ROW:
                System.out.println("You've entered invalid row value!");
                break;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column value!");
                break;
            case INCOMPATIBLE_LAND:
                System.out.println("You cant drop a this type of building on this type of texture!");
                break;
            case BUILDING_EXISTS_IN_THE_BLOCK:
                System.out.println("There's already a building in this location!");
                break;
            case SUCCESSFUL_DROP:
                System.out.println(" Building dropped successfully");
            default:
                System.out.println("Invalid input!");
                break;

        }

    }

    private static void selectBuilding(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages result = GameMenuController.selectBuilding(destination);
            if (result == GameMenuMessages.OPPONENT_BUILDING)
                System.out.println("This building doesn't belong to you!");
            else if (result == GameMenuMessages.EMPTY_LAND)
                System.out.println("There are no buildings in this location!");
            else if (result == GameMenuMessages.SUCCESSFUL_SELECT) System.out.println("building selected");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    private static void setTexture(String input) {
        String x = "", x1 = "", x2 = "", y = "", y1 = "", y2 = "";
        String type = "";
        int row, row1, row2, column, column1, column2;
        HashMap<String, String> options = InputProcessor.separateInput(input);
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-x":
                    x = option.getValue();
                    break;
                case "-x1":
                    x1 = option.getValue();
                    break;

                case "-x2":
                    x2 = option.getValue();
                    break;
                case "-y":
                    y = option.getValue();
                    break;
                case "-y1":
                    y1 = option.getValue();
                    break;
                case "-y2":
                    y2 = option.getValue();
                    break;
                case "-t":
                    type = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
                    return;
            }
        }
        if (type == null) {
            System.out.println("You should enter something for type!");
            return;
        }
        GameMenuMessages message;
        //TODO
        if (!x.equals("") && x1.equals("") && x2.equals("") && !y.equals("") && y1.equals("") && y2.equals("")) {
            row = Integer.parseInt(x);
            column = Integer.parseInt(y);
            message = GameMenuController.setTexture(type, row, column);
        }
        if (x.equals("") && !x1.equals("") && !x2.equals("") && y.equals("") && !y1.equals("") && !y2.equals("")) {
            row1 = Integer.parseInt(x1);
            row2 = Integer.parseInt(x2);
            column1 = Integer.parseInt(y1);
            column2 = Integer.parseInt(y2);
            message = GameMenuController.setTexture(type, row1, column1, row2, column2);
        } else {
            System.out.println("You haven't entered your inputs in a valid format");
            return;
        }
        switch (message) {
            case INVALID_ROW:
                System.out.println("You've entered invalid row!");
                return;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column!");
                return;
            case BUILDING_IN_THE_AREA:
                System.out.println("There's a building in the area you selected!");
                return;
            case BUILDING_EXISTS_IN_THE_BLOCK:
                System.out.println("There's a building in the block you selected!");
                return;
            case SET_TEXTURE_OF_BLOCK_SUCCESSFUL:
                System.out.println("You've successfully set the texture of block to " + type);
                return;
            case SET_TEXTURE_OF_AREA_SUCCESSFUL:
                System.out.println("You've successfully set the texture of area to " + type);
                return;
        }
    }

    private static void clear(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String x = "";
        String y = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {

                case "-x":
                    x = option.getValue();
                    break;
                case "-y":
                    y = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!x.matches("-?\\d+"))
            System.out.println("You should enter a number for row!");

        if (!y.matches("-?\\d+"))
            System.out.println("You should enter a number for column!");
        int row = Integer.parseInt(x);
        int column = Integer.parseInt(y);
        GameMenuMessages message = GameMenuController.clear(row, column);
        switch (message) {
            case INVALID_ROW:
                System.out.println("You've entered invalid row!");
                return;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column!");
                return;
            case NO_OWNED_ENTITY:
                System.out.println("You don't have anything to be cleared in this block");
                return;
            case SUCCESSFUL_CLEAR:
                System.out.println("You successfully");
                return;

        }

    }

    private static void dropRock(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String x = "";
        String y = "";
        String direction = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {

                case "-x":
                    x = option.getValue();
                    break;
                case "-y":
                    y = option.getValue();
                    break;
                case "-d":
                    direction = option.getValue();
                    break;
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!x.matches("-?\\d+")) {
            System.out.println("You should enter a number for row!");
            return;
        }

        if (!y.matches("-?\\d+")) {
            System.out.println("You should enter a number for column!");
            return;
        }
        if (!direction.matches("[a-z]")) {
            System.out.println("You've entered incorrect value for direction!");
            return;
        }

        int row = Integer.parseInt(x);
        int column = Integer.parseInt(y);
        GameMenuMessages message = GameMenuController.dropRock(row, column, direction);
        switch (message) {
            case INVALID_ROW:
                System.out.println("You've entered invalid row!");
                return;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column!");
                return;
            case INVALID_DIRECTION:
                System.out.println("You've entered invalid direction!");
                // TODO: is it possible to drop rock on non-empty land?
            case NON_EMPTY_LAND:
                System.out.println("You can't drop a rock here!");
                return;
            case SUCCESSFUL_DROP:
                System.out.println("You dropped a rock successfully");
                break;

        }

    }


    private static void dropTree(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = "";
        String x = "";
        String y = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-t":
                    type = option.getValue();
                    break;
                case "-x":
                    x = option.getValue();
                    break;
                case "-y":
                    y = option.getValue();
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!x.matches("-?\\d+"))
            System.out.println("You should enter a number for row!");

        if (!y.matches("-?\\d+"))
            System.out.println("You should enter a number for column!");
        int row = Integer.parseInt(x);
        int column = Integer.parseInt(y);
        GameMenuMessages message = GameMenuController.dropBuilding(row, column, type);
        switch (message) {

            case INVALID_ROW:
                System.out.println("You've entered invalid row value!");
                break;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column value!");
                break;
            case INCOMPATIBLE_LAND:
                System.out.println("You cant drop a this type of building on this type of texture!");
                break;
            case SUCCESSFUL_DROP:
                System.out.println(" Building dropped successfully");
            default:
                System.out.println("Invalid input!");
                break;

        }

    }


    private static void endTurn() {

    }

    private static void leaveGame() {

    }

}
