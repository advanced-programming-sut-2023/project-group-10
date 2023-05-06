package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Item;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.enums.messages.BuildingMenuMessages;

import java.util.ArrayList;

public class BuildingMenuController {

    public static Building selectedBuilding;

    public static void setSelectedBuilding(Building selectedBuilding) {
        BuildingMenuController.selectedBuilding = selectedBuilding;
    }

    public static BuildingMenuMessages createUnit(String type, int count) {
        if(!canBuildingCreateUnit())
            return BuildingMenuMessages.INVALID_BUILDING;

        if (true) //TODO complete
            return BuildingMenuMessages.INSUFFICIENT_RESOURCES;

        if (selectedBuilding.getGovernment().getPeasant() < count)
            return BuildingMenuMessages.INSUFFICIENT_POPULATION;

        if (isTypeCompatible(type))
            return BuildingMenuMessages.INCOMPATIBLE_TYPES;

        return BuildingMenuMessages.CREATE_UNIT_SUCCESSFUL;
    }

    public static BuildingMenuMessages repair() {
        if(!selectedBuilding.getBuildingType().isRepairable())
            return BuildingMenuMessages.NOT_CASTLE_BUILDING;
        if (stoneCounter() > selectedBuilding.getGovernment().getItemCount(Item.STONE))
            return BuildingMenuMessages.INSUFFICIENT_STONE;
        if (enemiesForceClose())
            return BuildingMenuMessages.ENEMIES_FORCES_ARE_CLOSE;

        selectedBuilding.setHitPoint(selectedBuilding.getBuildingType().getMaxHitPoint());
        return BuildingMenuMessages.REPAIR_SUCCESSFUL;
    }

    private static boolean enemiesForceClose(){
        int row = selectedBuilding.getPosition().row;
        int column = selectedBuilding.getPosition().column;
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        User owner = selectedBuilding.getGovernment().getOwner();

        int[] rowMove = {-1, 0, 0, 0, 1};
        int[] columnMove = {0, -1, 0, 1, 0};

        //TODO should i check if blocks are valid?
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                ArrayList<Unit> units = map.getBlockByRowAndColumn(row + rowMove[i], column + columnMove[j]).getAllUnits();
                for(Unit unit : units){
                    if(!unit.getGovernment().getOwner().equals(owner))
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean canBuildingCreateUnit(){
        return selectedBuilding.getBuildingType().getName().equals(BuildingTypeName.ENGINEER_GUILD) ||
                selectedBuilding.getBuildingType().getName().equals(BuildingTypeName.MERCENARY_POST) ||
                selectedBuilding.getBuildingType().getName().equals(BuildingTypeName.BARRACKS);
    }

    private static boolean isTypeCompatible(String type){
        BuildingTypeName buildingTypeName = selectedBuilding.getBuildingType().getName();
        MilitaryPersonRole militaryRole = (MilitaryPersonRole) Role.getRoleByName(RoleName.getRoleNameByNameString(type));

        if(militaryRole != null)
            return militaryRole.getProducingBuilding().equals(buildingTypeName);
        return false;
    }

    private static int stoneCounter(){
        return (selectedBuilding.getBuildingType().getMaxHitPoint() - selectedBuilding.getHitPoint()) / 2;
    }

}
