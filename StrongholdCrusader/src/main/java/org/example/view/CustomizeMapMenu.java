package org.example.view;

import org.example.controller.CustomizeMapController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.CustomizeMapCommands;
import org.example.view.enums.messages.CustomizeMapMessages;

import java.util.HashMap;
import java.util.Scanner;

public class CustomizeMapMenu {
    public static Map run() {
        CustomizeMapController.initializeMap();
        System.out.println("customize your map and enter \"end customization\" when you're done");
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.SET_TEXTURE) != null) setTexture(input);
            else if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.CLEAR) != null) clear(input);
            else if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.DROP_ROCK) != null) dropRock(input);
            else if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.DROP_TREE) != null) dropTree(input);
            else if (CustomizeMapCommands.getMatcher(input, CustomizeMapCommands.END_CUSTOMIZATION) != null) {

                return CustomizeMapController.getMap();
            }
            else if(input.matches("^\\s*show\\s+menu\\s+name\\s*$")) System.out.println("customize map menu");
            else System.out.println("invalid command!");
        }
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
        CustomizeMapMessages message = null;
        if (input.contains("-x1") && input.contains("-y1") && input.contains("-x2") && input.contains("-y2"))
            message = setTextureRectangleOfBlocks(options);
       else if (input.contains("-x") && input.contains("-y")) message = setTextureSingleBlock(options);

        else System.out.println("You haven't entered your inputs in a valid format");
        if (message == null) return;
        switch (message) {
            case INDEX_OUT_OF_BOUNDS:
                System.out.println("You've entered numbers less than 0 or greater than the size!");
                break;
            case POND_ENTERED:
                System.out.println("For setting texture to ponds you must only enter one block's coordinates!");
                break;
            case INVALID_LAND_TYPE:
                System.out.println("You've entered invalid type for texture!");
                break;
            case DROPPABlE_IN_THE_AREA:
                System.out.println("There's a building in the area you selected!");
                return;
            case DROPPABLE_IN_THE_BLOCK:
                System.out.println("There's a building in the block you selected!");
                return;
            case SET_TEXTURE_OF_BLOCK_SUCCESSFUL:
                System.out.println("You've successfully set the texture of block to " + type);
                return;
            case SET_TEXTURE_OF_AREA_SUCCESSFUL:
                System.out.println("You've successfully set the texture of area to " + type);
        }
    }

    private static CustomizeMapMessages setTextureRectangleOfBlocks(HashMap<String, String> options) {
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
            return CustomizeMapController.setTexture(type, point1, point2);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private static CustomizeMapMessages setTextureSingleBlock(HashMap<String, String> options) {
        String type = options.get("-t");
        options.remove("-t");
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y");
            return CustomizeMapController.setTexture(type, position);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private static void clear(String input) {
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            CustomizeMapMessages message = CustomizeMapController.clear(position);
            if (message == CustomizeMapMessages.INDEX_OUT_OF_BOUNDS) {
                System.out.println("You've entered numbers less than 0 or grater than the size!");
                return;
            } else if (message == CustomizeMapMessages.SUCCESSFUL_CLEAR)
                System.out.println("You successfully cleared the block!");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void dropRock(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String direction = options.getOrDefault("-d", "");
        if (direction == null) {
            System.out.println("empty direction field!");
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
            CustomizeMapMessages message = CustomizeMapController.dropRock(position, direction);
            switch (message) {
                case NON_EMPTY_LAND:
                    System.out.println("You can't drop a rock here!");
                    break;
                case DROP_ROCK_SUCCESSFUL:
                    System.out.println("You dropped a rock successfully!");
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
            CustomizeMapMessages message = CustomizeMapController.dropTree(position, type);
            switch (message) {
                case INDEX_OUT_OF_BOUNDS:
                    System.out.println("You've entered numbers less than 0 or grater than the size!");
                    break;
                case INVALID_TREE_TYPE:
                    System.out.println("You've entered invalid type for tree!");
                    break;
                case INCOMPATIBLE_LAND:
                    System.out.println("You cant drop  this type of Tree on this type of texture!");
                    break;
                case SUCCESSFUL_TREE_DROP:
                    System.out.println("Tree dropped successfully");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
