package org.example.view;

import org.example.controller.GameMenuController;
import org.example.model.User;
import org.example.model.game.Color;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.GameMenuCommands;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Scanner;

public class GameMenu {
    public static void run(HashMap<User, Color> players, Map map) {
        GameMenuController.initializeGame(players, map);
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY) != null) showPopularity();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FOOD_LIST) != null) showFoodList();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FOOD_RATE) != null) setFoodRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FOOD_RATE) != null) showFoodRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_TAX_RATE) != null) setTaxRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_TAX_RATE) != null) showTaxRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FEAR_RATE) != null) setFearRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FEAR_RATE) != null) showFearRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING) != null) dropBuilding(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_BUILDING) != null)
                selectBuilding(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT) != null) selectUnit(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.TRADE_MENU) != null) TradeMenu.run();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOP_MENU) != null) ShopMenu.run();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.NEXT_TURN) != null && endTurn() == GameMenuMessages.GAME_OVER)
                return;
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.LEAVE_GAME) != null) {
                leaveGame();
                return;
            }
        }
    }

    private static void showPopularityFactors() {
        System.out.println(GameMenuController.showPopularityFactors());
    }

    private static void showPopularity() {
        System.out.println(GameMenuController.showPopularity());
    }

    private static void showFoodList() {
        System.out.println(GameMenuController.showFoodList());
    }

    private static void setFoodRate(String input) {
        try {
            Integer foodRate = InputProcessor.rateInputProcessor(input);
            GameMenuMessages message = GameMenuController.setFoodRate(foodRate);
            switch (message) {
                case INVALID_FOOD_RATE:
                    System.out.println("Enter a number from -2 to 2!");
                    break;
                case SET_FOOD_RATE_SUCCESSFUL:
                    System.out.println("Food rate is set to " + foodRate);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void showFoodRate() {
        System.out.println("Food rate is : " + GameMenuController.showFoodRate());
    }


    private static void setTaxRate(String input) {
        try {
            Integer taxRate = InputProcessor.rateInputProcessor(input);
            GameMenuMessages message = GameMenuController.setFoodRate(taxRate);
            switch (message) {
                case INVALID_TAX_RATE:
                    System.out.println("Enter a number from -3 to 8!");
                    break;
                case SET_FOOD_TAX_SUCCESSFUL:
                    System.out.println("Food rate is set to " + taxRate);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void showTaxRate() {
        System.out.println("Tax rate is : " + GameMenuController.showTaxRate());
    }


    private static void showFearRate() {
        System.out.println("Fear rate is : " + GameMenuController.showFearRate());
    }


    private static void setFearRate(String input) {
        try {
            Integer fearRate = InputProcessor.rateInputProcessor(input);
            GameMenuMessages message = GameMenuController.setFoodRate(fearRate);
            switch (message) {
                case INVALID_TAX_RATE:
                    System.out.println("Enter a number from -5 to 5!");
                    break;
                case SET_FOOD_TAX_SUCCESSFUL:
                    System.out.println("Food rate is set to " + fearRate);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void dropBuilding(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = options.getOrDefault("-t", "");
        if (type == null) {
            System.out.println("empty type field");
            return;
        }
        if (type.isEmpty()) {
            System.out.println("missing building type");
            return;
        }
        options.remove("-t");
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            GameMenuMessages message = GameMenuController.dropBuilding(position, type);
            switch (message) {
                case INVALID_BUILDING_TYPE:
                    System.out.println("You've entered invalid building type!");
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
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
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

    private static void selectUnit(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages result = GameMenuController.selectUnit(destination);
            if (result == GameMenuMessages.NO_UNITS_FOUND) System.out.println("there aren't any units here");
            else if (result == GameMenuMessages.SUCCESSFUL_SELECT) System.out.println("units selected");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static GameMenuMessages endTurn() {
        return null;
    }

    private static void leaveGame() {

    }
}