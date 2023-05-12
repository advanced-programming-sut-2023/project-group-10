package org.example.view;

import org.example.controller.MapMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.MapMenuCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_MAP) != null) showMap(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.MOVE_MAP) != null) moveMap(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_DETAILS) != null) showDetails(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_EXTENDED_DETAILS) != null) showExtendedDetails(input);

            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.BACK) != null) return;
            else if (input.matches("^\\s*show\\s+menu\\s+name\\s*$")) System.out.println("map menu");
            else System.out.println("invalid command!");
        }
    }

    private static void showExtendedDetails(String input) {
        try {
            Coordinate blockPosition = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            System.out.println(MapMenuController.showDetailsExtended(blockPosition));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void showMap(String input) {
        try {
            Coordinate origin = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            System.out.println(MapMenuController.showMap(origin));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void moveMap(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        int horizontalChange = 0;
        int verticalChange = 0;
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getValue() == null) option.setValue("1");
            switch (option.getKey()) {
                case "-u":
                    if (!option.getValue().matches("-?\\d+")) {
                        System.out.println("invalid argument for -u");
                        return;
                    }
                    verticalChange -= Integer.parseInt(option.getValue());
                    break;
                case "-d":
                    if (!option.getValue().matches("-?\\d+")) {
                        System.out.println("invalid argument for -d");
                        return;
                    }
                    verticalChange += Integer.parseInt(option.getValue());
                    break;
                case "-l":
                    if (!option.getValue().matches("-?\\d+")) {
                        System.out.println("invalid argument for -l");
                        return;
                    }
                    horizontalChange -= Integer.parseInt(option.getValue());
                    break;
                case "-r":
                    if (!option.getValue().matches("-?\\d+")) {
                        System.out.println("invalid argument for -r");
                        return;
                    }
                    horizontalChange += Integer.parseInt(option.getValue());
                    break;
                default:
                    System.out.println("invalid option");
                    return;
            }
        }
        System.out.println(MapMenuController.moveMap(horizontalChange, verticalChange));
    }

    private static void showDetails(String input) {
        try {
            Coordinate blockPosition = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            System.out.println(MapMenuController.showDetails(blockPosition));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}