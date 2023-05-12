package org.example.view;

import org.example.controller.MountEquipmentMenuController;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.SiegeEquipment;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.messages.MountEquipmentMenuMessages;

import java.util.Scanner;

public class MountEquipmentMenu {

    public static void run(SiegeEquipment siegeEquipment) {
        int count = siegeEquipment.getCurrentNeededEngineers();
        MountEquipmentMenuController.setup(count, siegeEquipment);
        if (count == 0) {
            System.out.println("equipment is already full");
            return;
        }
        System.out.println(siegeEquipment.getRole().getName() + " is selected, enter each engineer's coordinate (-x [x] -y [y])");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("you need " + count + " more engineers");
            String input = scanner.nextLine();
            if (input.matches("\\s*cancel\\s*")) {
                System.out.println("equipment remains unmounted and engineers are in their original position");
                return;
            }
            try {
                Coordinate coordinate = InputProcessor.getCoordinateFromXYInput(input, "-x", "-y");
                MountEquipmentMenuMessages result = MountEquipmentMenuController.selectEngineers(coordinate);
                if (result == MountEquipmentMenuMessages.ALREADY_SELECTED)
                    System.out.println("this block has already been selected");
                else if (result == MountEquipmentMenuMessages.NO_ENGINEERS)
                    System.out.println("no engineers are available at this block");
                else if (result == MountEquipmentMenuMessages.SELECTION_SUCCESSFUL) {
                    count = MountEquipmentMenuController.getUnselectedEngineersCount();
                    System.out.println("engineer(s) selected successfully, " + count + " engineers left to go.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (count == 0) {
                System.out.println("equipment was mounted successfully");
                MountEquipmentMenuController.completeMountingProcess();
                return;
            }
        }
    }
}
