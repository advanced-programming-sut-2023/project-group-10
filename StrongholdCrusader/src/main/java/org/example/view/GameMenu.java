package org.example.view;

import org.example.controller.GameMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.GameMenuCommands;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GameMenu {
    public static void run(HashMap<String, String> players, HashMap<String, Coordinate> keeps, Map map) {
        GameMenuController.initializeGame(players, keeps, map);
        getCurrentPlayer();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.ROUNDS_PLAYED) != null) showRoundsPlayed();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_PLAYER) != null) getCurrentPlayer();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY) != null) showPopularity();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FOOD_LIST) != null) showFoodList();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FOOD_RATE) != null) setFoodRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FOOD_RATE) != null) showFoodRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_TAX_RATE) != null) setTaxRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_TAX_RATE) != null) showTaxRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FEAR_RATE) != null) setFearRate(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FEAR_RATE) != null) showFearRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING) != null) dropBuilding(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_UNIT) != null) dropUnit(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_BUILDING) != null)
                selectBuilding(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT) != null) selectUnit(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.MOUNT_EQUIPMENT) != null)
                mountEquipment(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.CLEAR_FORCES) != null) clearForces(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DELETE_STRUCTURE) != null)
                deleteStructure(input);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.TRADE_MENU) != null) TradeMenu.run();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOP_MENU) != null) ShopMenu.run();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.MAP_MENU) != null) MapMenu.run();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.NEXT_TURN) != null)
                if (endTurn() == GameMenuMessages.GAME_OVER) return;
                else getCurrentPlayer();
            else if (input.matches("^\\s*show\\s+menu\\s+name\\s*$")) System.out.println("game menu");
            else System.out.println("invalid command!");
        }
    }

    private static void showRoundsPlayed() {
        System.out.println(GameMenuController.showRoundsPlayed() + " rounds are played!");
    }

    private static void getCurrentPlayer() {
        System.out.println("It is " + GameMenuController.currentPlayer().getUsername() + "'s turn to play");
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
                case INSUFFICIENT_FOOD:
                    System.out.println("You don't have sufficient supplies for this rate, try again!");
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
            GameMenuMessages message = GameMenuController.setTaxRate(taxRate);
            switch (message) {
                case INVALID_TAX_RATE:
                    System.out.println("Enter a number from -3 to 8!");
                    break;
                case SET_TAX_RATE_SUCCESSFUL:
                    System.out.println("Tax rate is set to " + taxRate);
                case INSUFFICIENT_GOLD:
                    System.out.println("You entered a negative number for tax rate, but you don't have enough gold!");
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
            GameMenuMessages message = GameMenuController.setFearRate(fearRate);
            switch (message) {
                case INVALID_FEAR_RATE:
                    System.out.println("Enter a number from -5 to 5!");
                    break;
                case SET_FEAR_RATE_SUCCESSFUL:
                    System.out.println("Fear rate is set to " + fearRate);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void dropUnit(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = options.getOrDefault("-t", "");
        String countAsString = options.getOrDefault("-c", "");
        if (type == null) {
            System.out.println("empty type field");
            return;
        }
        options.remove("-t");

        if (countAsString == null) {
            System.out.println("empty count field");
            return;
        }
        if (type.isEmpty()) {
            System.out.println("missing unit type");
            return;
        }
        if (!Pattern.compile("-?\\d+").matcher(countAsString).matches()) {
            System.out.println("You should enter number for count(-c)");
            return;
        }
        options.remove("-c");
        int count = Integer.parseInt(countAsString);

        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            GameMenuMessages message = GameMenuController.dropUnit(position, type, count);
            switch (message) {
                case INVALID_UNIT_TYPE:
                    System.out.println("You've entered invalid unit type!");
                    break;
                case INVALID_UNIT_COUNT:
                    System.out.println("You cant drop 0 or negative amount of units!");
                    break;
                case UNWALKABLE_LAND:
                    System.out.println("Unit can't go here!");
                    break;
                case SUCCESSFUL_DROP:
                    System.out.println("Unit dropped successfully");
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;

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
                    System.out.println("You cant drop this type of building on this type of texture!");
                    break;
                case BUILDING_EXISTS_IN_THE_BLOCK:
                    System.out.println("This location is full!");
                    break;
                case SUCCESSFUL_DROP:
                    System.out.println(" Building dropped successfully");
                    break;
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
            else if (result == GameMenuMessages.SUCCESSFUL_SELECT) {
                System.out.println("building selected");
                BuildingMenu.run(destination);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void selectUnit(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages result = GameMenuController.selectUnit(destination);
            if (result == GameMenuMessages.NO_UNITS_FOUND) System.out.println("there aren't any units here");
            else if (result == GameMenuMessages.SUCCESSFUL_SELECT) {
                System.out.println("units selected");
                UnitMenu.run();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void mountEquipment(String input) {
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages result = GameMenuController.mountEquipment(position);
            if (result == GameMenuMessages.NO_EQUIPMENT_FOUND)
                System.out.println("no equipment available at this block");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void clearForces(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuController.clearForces(destination);
            System.out.println("units cleared successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void deleteStructure(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages result = GameMenuController.deleteStructure(destination);
            if (result == GameMenuMessages.NO_STRUCTURE) System.out.println("you have no property in this block");
            else if (result == GameMenuMessages.NOT_YOUR_STRUCTURE)
                System.out.println("you can't delete structures that you don't own");
            else if (result == GameMenuMessages.SUCCESSFUL_DELETE_STRUCTURE)
                System.out.println("structure was deleted successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static GameMenuMessages endTurn() {

        return GameMenuController.goToNextPlayer();
    }

}