package org.example.controller;

import org.example.model.game.buildings.Building;
import org.example.view.enums.messages.BuildingMenuMessages;

public class BuildingMenuController {

    private static Building selectedBuilding;

    public static void setSelectedBuilding(Building selectedBuilding) {
        BuildingMenuController.selectedBuilding = selectedBuilding;
    }

    public static BuildingMenuMessages createUnit(String type, int count) {
        if (true) {
            return BuildingMenuMessages.INVALID_ROW;
        }
        if (true) {
            return BuildingMenuMessages.INVALID_COLUMN;
        }
        if (true) {
            return BuildingMenuMessages.INSUFFICIENT_RESOURCES;
        }
        if (true) {
            return BuildingMenuMessages.INSUFFICIENT_POPULATION;
        }
        if (true) {
            return BuildingMenuMessages.INCOMPATIBLE_TYPES;

        }
        return BuildingMenuMessages.CREATE_UNIT_SUCCESSFUL;

    }

    public BuildingMenuMessages repair() {
        if (true)
            return BuildingMenuMessages.INSUFFIECIENT_STONE;
        if (true)
            return BuildingMenuMessages.ENEMIES_FORCES_ARE_CLOSE;

        return BuildingMenuMessages.REPAIR_SUCCESSFUL;
    }


    // UNIT RELATED
    public static BuildingMenuMessages selectUnit(int row, int column) {
        return null;
    }

}
