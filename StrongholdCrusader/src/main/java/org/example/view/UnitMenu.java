package org.example.view;

import org.example.controller.UnitMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.UnitMenuCommands;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.HashMap;
import java.util.Scanner;

public class UnitMenu {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.MOVE_UNIT) != null) moveUnit(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.PATROL_UNIT) != null) patrolUnit(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.SET_STATE) != null) setState(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK) != null) attack(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.POUR_OIL) != null) pourOil(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.DIG_TUNNEL) != null) digTunnel(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.BUILD) != null) build(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.DISBAND) != null) disband();
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.BACK) != null) return;
        }
    }

    private static void moveUnit(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.moveUnit(destination);
            if (result == UnitMenuMessages.INVALID_DESTINATION) System.out.println("you can't go there");
            else System.out.println("units are on their way");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void patrolUnit(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        try {
            HashMap<String, String> startingPointOptions = new HashMap<>();
            startingPointOptions.put("-x1", options.getOrDefault("-x1", ""));
            startingPointOptions.put("-y1", options.getOrDefault("-y1", ""));
            Coordinate startPoint = InputProcessor.getCoordinateFromXYInput(startingPointOptions, "-x1", "-y1");
            HashMap<String, String> destinationOptions = new HashMap<>();
            destinationOptions.put("-x1", options.getOrDefault("-x1", ""));
            destinationOptions.put("-y1", options.getOrDefault("-y1", ""));
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(destinationOptions, "-x2", "-y2");
            if (options.size() != 4) {
                System.out.println("invalid options");
                return;
            }
            UnitMenuMessages result = UnitMenuController.patrolUnit(startPoint, destination);
            if (result == UnitMenuMessages.TOUR_BLOCKED) System.out.println("can't find a way");
            else if (result == UnitMenuMessages.INVALID_DESTINATION) System.out.println("can't go there");
            else System.out.println("units are on patrol");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setState(String input) {

    }

    private static void attack(String input) {

    }

    private static void airAttack(String input) {

    }

    private static void attackEnemy(String input) {

    }

    private static void pourOil(String input) {

    }

    private static void digTunnel(String input) {

    }

    private static void build(String input) {

    }

    private static void disband() {

    }
}
