package org.example.view;

import org.example.controller.BuildingMenuController;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.BuildingMenuCommands;
import org.example.view.enums.messages.BuildingMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuildingMenu {
    public static void run(String input){
        System.out.println("hitpoints: "+BuildingMenuController.selectedBuilding.getHitPoint());
        while (true) {
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.CREATE_UNIT) != null)
                createUnit(input);
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.SELECT_UNIT) != null) {
                selectUnit(input);
                //run unit menu?
                break;// not sure!
            }
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.REPAIR) != null)
                repair();
            if (BuildingMenuCommands.getMatcher(input, BuildingMenuCommands.BACK) != null) {
                //back to game menu
            }
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
        }
    }
    private static void createUnit(String input){
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
        int count=Integer.parseInt(c);
        BuildingMenuMessages message= BuildingMenuController.createUnit(type,count);
        switch (message){
            case INVALID_ROW:
                System.out.println("You've entered invalid row");
                break;
            case INVALID_COLUMN:
                System.out.println("You've entered invalid column");
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
                System.out.println("you successfully created unit with type "+type);
                break;

        }
    }
    private static void repair(){
        BuildingMenuMessages message= BuildingMenuController.repair();
        switch (message){
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
    private static void selectUnit(String input){

    }

}
