package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Moat;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    public static ArrayList<MilitaryUnit> selectedMilitaryUnits;

    public static UnitMenuMessages moveUnit(Coordinate destination) {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination.row, destination.column).getTexture().isWalkable())
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.moveUnit(destination);
        return UnitMenuMessages.SUCCESSFUL_MOVE_UNIT;
    }

    // TODO
    public static UnitMenuMessages patrolUnit(Coordinate startPoint, Coordinate destination) {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(startPoint.row, startPoint.column).getTexture().isWalkable() ||
                !Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination.row, destination.column).getTexture().isWalkable())
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.patrol(startPoint, destination);
        return UnitMenuMessages.SUCCESSFUL_MOVE_UNIT;
    }

    public static UnitMenuMessages setStance(Coordinate position, MilitaryUnitStance stance) {
        //TODO: check and complete doc:pg.23
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits) {
            selectedMilitaryUnit.changeStance(stance);
        }
        return UnitMenuMessages.SUCCESSFUL_SET_STANCE;
    }
    //TODO: decide to put it here or in the model

    private static boolean lookForEngineer(ArrayList<MilitaryUnit> units) {
        for (MilitaryUnit unit : units) {
            if (unit.getRole().equals(MilitaryPersonRole.getRoleByName(RoleName.ENGINEER))) return true;
        }
        return false;
    }

    public static UnitMenuMessages build(MilitaryEquipmentRole equipmentRole) {
        if (!lookForEngineer(selectedMilitaryUnits)) return UnitMenuMessages.UNITS_CANT_BUILD;
        //TODO: implement building items
        return null;
    }

    public static UnitMenuMessages attackEnemy(Coordinate target) {
        return null;
    }

    public static UnitMenuMessages airAttack(Coordinate target) {

        return null;
    }

    public static UnitMenuMessages pourOil(String direction) {

        return null;
    }

    public static UnitMenuMessages digMoat(Coordinate position) {
        UnitMenuMessages moveResult;
        if ((moveResult = moveUnit(position)) != UnitMenuMessages.SUCCESSFUL_MOVE_UNIT) return moveResult;
        boolean canDigMoats = false;
        for (Unit selectedUnit : selectedMilitaryUnits)
            if (selectedUnit instanceof MilitaryPerson && ((MilitaryPersonRole) selectedUnit.getRole()).isCanDigMoats()) {
                canDigMoats = true;
                break;
            }
        if (!canDigMoats) return UnitMenuMessages.UNITS_CANT_DIG_MOAT;
        new Moat(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        return UnitMenuMessages.SUCCESSFUL_DIG_MOAT;
    }


    public static UnitMenuMessages disband() {
        for (MilitaryUnit selectedUnit : selectedMilitaryUnits) {
            new Unit(selectedUnit.getPosition(), RoleName.PEASANT, selectedUnit.getGovernment());
            selectedUnit.getGovernment().deleteUnit(selectedUnit);
        }
        selectedMilitaryUnits = null;
        return UnitMenuMessages.SUCCESSFUL_DISBAND;
    }
}