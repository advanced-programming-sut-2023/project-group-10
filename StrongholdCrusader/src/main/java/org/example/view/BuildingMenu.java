package org.example.view;

import org.example.controller.BuildingMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.BuildingMenuCommands;
import org.example.view.enums.messages.BuildingMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuildingMenu {
    public static void run(Coordinate coordinate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("hitpoints: " + BuildingMenuController.selectedBuilding.getHitPoint());
        while (true) {
            String input = scanner.nextLine();
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.CREATE_UNIT) != null)
                createUnit(input);
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.REPAIR) != null)
                repair();
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.BACK) != null)
                return;
        }
    }
    private static void createUnit(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = "";
        String c = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {

                case "-c":
                    c = option.getValue();
                    break;
                case "-t":
                    type = option.getValue();
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!c.matches("\\d+"))
            System.out.println("You should enter a number for row!");
        int count = Integer.parseInt(c);
        BuildingMenuMessages message = BuildingMenuController.createUnit(type, count);
        switch (message) {
            case INVALID_BUILDING:
                System.out.println("You building should be barracks or engineers guild or mercenary post!");
                break;
            case INSUFFICIENT_RESOURCES:
                //should we  mention which resource?
                System.out.println("You don't have enough resources!");
                break;
            case INSUFFICIENT_POPULATION:
                System.out.println("There's not enough population to create this unit");
                break;
            case INCOMPATIBLE_TYPES:
                // TODO: more precise message?
                System.out.println("A mismatch of types accord");
                break;
            case CREATE_UNIT_SUCCESSFUL:
                System.out.println("you successfully created unit with type " + type);
                break;

        }
    }

    private static void repair() {
        BuildingMenuMessages message = BuildingMenuController.repair();
        switch (message) {
            case NOT_CASTLE_BUILDING:
                System.out.println("You must select a castle building to repair!");
                break;
            case INSUFFICIENT_STONE:
                System.out.println("You don't have enough stone to repair this building");
                break;
            case ENEMIES_FORCES_ARE_CLOSE:
                System.out.println("Some of enemy's units are too close,try to repair later!");
                break;
            case REPAIR_SUCCESSFUL:
                System.out.println("You've successfully repaired this building");
        }
    }

}
