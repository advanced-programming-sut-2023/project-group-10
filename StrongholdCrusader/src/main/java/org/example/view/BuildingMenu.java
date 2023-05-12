package org.example.view;

import org.example.controller.BuildingMenuController;
import org.example.model.Stronghold;
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
        BuildingMenuController.setSelectedBuilding
                (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(coordinate).getBuilding());
        System.out.println("Hitpoints: " + BuildingMenuController.selectedBuilding.getHitPoint());

        while (true) {
            String input = scanner.nextLine();
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.CREATE_UNIT) != null)
                createUnit(input);
            else if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.REPAIR) != null)
                repair();
            else if (input.matches("^\\s*show\\s+menu\\s+name\\s*$"))
                System.out.println("building menu");
            else if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.BACK) != null)
                return;
            else
                System.out.println("invalid command!");
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
                    break;
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
            case INVALID_UNIT_TYPE:
                System.out.println("Invalid unit type!");
                break;
            case INSUFFICIENT_RESOURCES:
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
            case AT_MAX_HITPOINT:
                System.out.println("Building has already its max hitpoint!");
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
