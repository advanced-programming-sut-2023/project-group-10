package org.example.view;

import org.example.controller.UnitMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.UnitMenuCommands;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UnitMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.MOVE_UNIT) != null) moveUnit(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.PATROL_UNIT) != null) patrolUnit(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.SET_STANCE) != null) setStance(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK) != null) attack(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.ASSIGN_TO_OIL_DUTY) != null)
                assignToOilDuty(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.POUR_OIL) != null) pourOil(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.DIG_TUNNEL) != null) digTunnel(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.DIG_MOAT) != null) digMoat(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.BUILD) != null) build(input);
            else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.DISBAND) != null) {
                disband();
                if (UnitMenuController.selectedMilitaryUnits == null) return;
            } else if (UnitMenuCommands.getMatcher(input, UnitMenuCommands.BACK) != null) return;
        }
    }

    private static void moveUnit(String input) {
        try {
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.moveUnit(destination);
            if (result == UnitMenuMessages.INVALID_DESTINATION) System.out.println("you can't go there");
            else if (result == UnitMenuMessages.SUCCESSFUL_MOVE_UNIT) System.out.println("units are on their way");
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
            destinationOptions.put("-x2", options.getOrDefault("-x2", ""));
            destinationOptions.put("-y2", options.getOrDefault("-y2", ""));
            Coordinate destination = InputProcessor.getCoordinateFromXYInput(destinationOptions, "-x2", "-y2");
            if (options.size() != 4) {
                System.out.println("invalid options");
                return;
            }
            UnitMenuMessages result = UnitMenuController.patrolUnit(startPoint, destination);
            if (result == UnitMenuMessages.TOUR_BLOCKED) System.out.println("can't find a way");
            else if (result == UnitMenuMessages.INVALID_DESTINATION) System.out.println("can't go there");
            else if (result == UnitMenuMessages.SUCCESSFUL_PATROL) System.out.println("units are on patrol");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setStance(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        try {
            HashMap<String, String> positionOptions = new HashMap<>();
            positionOptions.put("-x", options.getOrDefault("-x", ""));
            positionOptions.put("-y", options.getOrDefault("-y", ""));
            Coordinate position = InputProcessor.getCoordinateFromXYInput(positionOptions, "-x", "-y");
            if (!options.containsValue("-s")) {
                System.out.println("choose a stance");
                return;
            }
            MilitaryUnitStance stance = MilitaryUnitStance.getStanceByName(options.get("-s"));
            if (stance == null) {
                System.out.println("invalid stance. choose standing, defensive or offensive");
                return;
            }
            UnitMenuMessages result = UnitMenuController.setStance(position, stance);
            if (result == UnitMenuMessages.SUCCESSFUL_SET_STANCE) System.out.println("stance was set successfully");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void attack(String input) {
        if (input.contains("-ex") && input.contains("-ey")) attackEnemy(input);
        else if (input.contains("-x") && input.contains("-y")) airAttack(input);
        else System.out.println("invalid options");
    }

    private static void airAttack(String input) {
        try {
            Coordinate target = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.airAttack(target);
            if (result == UnitMenuMessages.TARGET_OUT_OF_RANGE)
                System.out.println("can't attack there, it's too far away");
            else if (result == UnitMenuMessages.SUCCESSFUL_ENEMY_ATTACK)
                System.out.println("units are attacking the target");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void attackEnemy(String input) {
        try {
            Coordinate target = InputProcessor.getCoordinateFromXYInput(input, "-ex", "-ey");
            UnitMenuMessages result = UnitMenuController.attackEnemy(target);
            if (result == UnitMenuMessages.NO_ENEMY_HERE) System.out.println("enemy not found");
            else if (result == UnitMenuMessages.TARGET_OUT_OF_RANGE)
                System.out.println("none of the selected units can reach the enemy");
            else if (result == UnitMenuMessages.SUCCESSFUL_ENEMY_ATTACK)
                System.out.println("units are attacking the target");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void assignToOilDuty(String input) {
        try {
            Coordinate location = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.assignToOilDuty(location);
            if (result == UnitMenuMessages.INVALID_TARGET) System.out.println("no oil smelter here");
            else if (result == UnitMenuMessages.UNITS_CANT_POUR_OIL)
                System.out.println("no engineers on oil duty found");
            else if (result == UnitMenuMessages.ALREADY_HAS_OIL) System.out.println("the engineer's pot is full");
            else if (result == UnitMenuMessages.SUCCESSFUL_ASSIGN_TO_OIL_DUTY)
                System.out.println("the engineer is going to the oil smelter");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void pourOil(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String direction = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-d")) direction = option.getValue();
            else {
                System.out.println("invalid option");
                return;
            }
        }
        if (direction == null) System.out.println("empty field");
        else if (direction.isEmpty()) System.out.println("missing option -d");
        else {
            UnitMenuMessages result = UnitMenuController.pourOil(direction);
            if (result == UnitMenuMessages.INVALID_DIRECTION) System.out.println("invalid direction");
            else if (result == UnitMenuMessages.UNITS_CANT_POUR_OIL)
                System.out.println("only engineers on oil duty can pour oil");
            else if (result == UnitMenuMessages.TARGET_OUT_OF_MAP) System.out.println("target must be inside the map");
            else if (result == UnitMenuMessages.SUCCESSFUL_POUR_OIL) System.out.println("oil was poured successfully");
        }
    }

    private static void digTunnel(String input) {
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.digTunnel(position);
            if (result == UnitMenuMessages.INVALID_TARGET) System.out.println("can't dig a tunnel there");
            else if (result == UnitMenuMessages.INVALID_TUNNEL_UNIT)
                System.out.println("these units can't dig a tunnel");
            else if (result == UnitMenuMessages.NO_ENEMY_CASTLE)
                System.out.println("no enemy castle is accessible from this location");
            else if (result == UnitMenuMessages.SUCCESSFUL_DIG_TUNNEL)
                System.out.println("units are on their way to dig a tunnel");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void digMoat(String input) {
        try {
            Coordinate position = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
            UnitMenuMessages result = UnitMenuController.digMoat(position);
            if (result == UnitMenuMessages.INVALID_TARGET) System.out.println("can't dig a moat here");
            else if (result == UnitMenuMessages.UNITS_CANT_DIG_MOAT)
                System.out.println("none of these units can dig a moat");
            else if (result == UnitMenuMessages.SUCCESSFUL_DIG_MOAT)
                System.out.println("units are on their way to dig a moat");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void build(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String equipment = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-q")) equipment = option.getValue();
            else {
                System.out.println("invalid option");
                return;
            }
        }
        if (equipment == null) {
            System.out.println("empty field");
            return;
        }
        if (equipment.isEmpty()) {
            System.out.println("missing option -q");
            return;
        }
        Role equipmentRole = MilitaryEquipmentRole.getRoleByName(RoleName.getRoleNameByNameString(equipment));
        if (!(equipmentRole instanceof MilitaryEquipmentRole)) {
            System.out.println("invalid equipment type");
            return;
        }
        UnitMenuMessages result = UnitMenuController.build((MilitaryEquipmentRole) equipmentRole);
        if (result == UnitMenuMessages.UNITS_CANT_BUILD) System.out.println("only engineers can build equipment");
        else if (result == UnitMenuMessages.INSUFFICIENT_RESOURCES)
            System.out.println("you don't have the required resources");
        else if (result == UnitMenuMessages.SUCCESSFUL_BUILD) System.out.println("engineer started building equipment");
    }

    private static void disband() {
        UnitMenuMessages result = UnitMenuController.disband();
        if (result == UnitMenuMessages.SUCCESSFUL_DISBAND) System.out.println("units were disbanded");
    }
}
