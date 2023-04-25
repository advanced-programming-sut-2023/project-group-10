package org.example.view;

import org.example.controller.GameMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.GameMenuCommands;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Scanner;

public class GameMenu {
    public static void run() {
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

    private static void setTexture(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = options.getOrDefault("-t", "");
        if (type == null) {
            System.out.println("You should enter something for type!");
            return;
        }
        if (type.isEmpty()) {
            System.out.println("missing type option");
            return;
        }
        GameMenuMessages message = null;
        if (input.contains("-x") && input.contains("-y")) message = setTextureSingleBlock(options);
        else if (input.contains("-x1") && input.contains("-y1") && input.contains("-x2") && input.contains("-y2"))
            message = setTextureRectangleOfBlocks(options);
        else System.out.println("You haven't entered your inputs in a valid format");
        if (message == null) return;
        switch (message) {
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
        }
    }

    private static GameMenuMessages setTextureRectangleOfBlocks(HashMap<String, String> options) {
        String type = options.get("-t");
        options.remove("-t");
        try {
            HashMap<String, String> pointOneOptions = new HashMap<>();
            pointOneOptions.put("-x1", options.get("-x1"));
            pointOneOptions.put("-y1", options.get("-y1"));
            Coordinate point1 = InputProcessor.getCoordinateFromXYInput(pointOneOptions, "-x1", "-y1");
            HashMap<String, String> pointTwoOptions = new HashMap<>();
            pointTwoOptions.put("-x2", options.get("-x2"));
            pointTwoOptions.put("-y2", options.get("-y2"));
            Coordinate point2 = InputProcessor.getCoordinateFromXYInput(pointTwoOptions, "-x2", "-y2");
            if (options.size() != 4) {
                System.out.println("invalid options");
                return null;
            }
            return GameMenuController.setTexture(type, point1, point2);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private static GameMenuMessages setTextureSingleBlock(HashMap<String, String> options) {
        String type = options.get("-t");
        options.remove("-t");
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            return GameMenuController.setTexture(type, position);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private static void clear(String input) {
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            GameMenuMessages message = GameMenuController.clear(position);
            if (message == GameMenuMessages.SUCCESSFUL_CLEAR) System.out.println("You successfully cleared the block");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void dropRock(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String direction = options.getOrDefault("-d", "");
        if (direction == null) {
            System.out.println("empty direction field");
            return;
        }
        if (direction.isEmpty()) {
            System.out.println("missing direction option");
            return;
        }
        if (!direction.matches("[newsr]")) {
            System.out.println("You've entered invalid direction!");
            return;
        }
        options.remove("-d");
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            GameMenuMessages message = GameMenuController.dropRock(position, direction);
            switch (message) {
                case NON_EMPTY_LAND:
                    System.out.println("You can't drop a rock here!");
                    break;
                case SUCCESSFUL_DROP:
                    System.out.println("You dropped a rock successfully");
                    break;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void dropTree(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = options.getOrDefault("-t", "");
        if (type == null) {
            System.out.println("empty type field");
            return;
        }
        if (type.isEmpty()) {
            System.out.println("missing tree type");
            return;
        }
        options.remove("-t");
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            GameMenuMessages message = GameMenuController.dropBuilding(position, type);
            switch (message) {
                case INCOMPATIBLE_LAND:
                    System.out.println("You cant drop a this type of building on this type of texture!");
                    break;
                case SUCCESSFUL_DROP:
                    System.out.println(" Building dropped successfully");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void leaveGame() {

    }
}