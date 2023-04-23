package org.example.view;

import org.example.controller.MapMenuController;
import org.example.model.Stronghold;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.MapMenuCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapMenu {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_MAP) != null) showMap(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.MOVE_MAP) != null) moveMap(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_DETAILS) != null) showDetails(input);
            else if (MapMenuCommands.getMatcher(input, MapMenuCommands.BACK) != null) return;
        }
    }

    private static void showMap(String input) {
        Coordinate origin = getCoordinateFromXYInput(input);
        if (origin == null) return;
        System.out.println(MapMenuController.showMap(origin));
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
        Coordinate blockPosition = getCoordinateFromXYInput(input);
        if (blockPosition == null) return;
        System.out.println(MapMenuController.showDetails(blockPosition));
    }

    private static Coordinate getCoordinateFromXYInput(String input) {
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
                    return null;
            }
        }
        if (x == null || y == null) {
            System.out.println("empty field");
            return null;
        }
        if (x.isEmpty() || y.isEmpty()) {
            System.out.println("missing option");
            return null;
        }
        if (!x.matches("\\d+")) {
            System.out.println("invalid x");
            return null;
        }
        if (!y.matches("\\d+")) {
            System.out.println("invalid y");
            return null;
        }
        org.example.model.game.envirnmont.Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.isIndexInBounds(Integer.parseInt(x))) {
            System.out.println("x out of bounds");
            return null;
        }
        if (!map.isIndexInBounds(Integer.parseInt(y))) {
            System.out.println("y out of bounds");
            return null;
        }

        return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
    }
}
