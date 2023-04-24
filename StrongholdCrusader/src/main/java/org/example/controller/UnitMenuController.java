package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Moat;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    public static ArrayList<Unit> selectedMilitaryUnits;

    public static UnitMenuMessages moveUnit(Coordinate destination) {
        return null;
    }

    public static UnitMenuMessages patrolUnit(Coordinate startPoint, Coordinate destination) {
        return null;
    }

    public static UnitMenuMessages setState(int x, int y, String state) {
        return null;
    }

    public static UnitMenuMessages attack(int x, int y) {
        return null;
    }

    public static UnitMenuMessages pourOil(String direction) {
        return null;
    }

    public static UnitMenuMessages digTunnel(int x, int y) {
        return null;
    }

    public static UnitMenuMessages digMoat(int x, int y) {
        UnitMenuMessages moveResult;
        if ((moveResult = moveUnit(x, y)) != UnitMenuMessages.SUCCESSFUL_MOVE_UNIT) return moveResult;
        boolean canDigMoats = false;
        for (Unit selectedUnit : selectedMilitaryUnits)
            if (selectedUnit instanceof MilitaryPerson && ((MilitaryPersonRole) selectedUnit.getRole()).isCanDigMoats()) {
                canDigMoats = true;
                break;
            }
        if (!canDigMoats) return UnitMenuMessages.UNITS_CANT_DIG_MOAT;
        new Moat(new Coordinate(x, y), Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        return UnitMenuMessages.SUCCESSFUL_DIG_MOAT;
    }

    public static UnitMenuMessages build(String equipmentName) {
        return null;
    }

    public static UnitMenuMessages disband() {
        for (Unit selectedUnit : selectedMilitaryUnits) {
            new Unit(selectedUnit.getPosition(), RoleName.PEASANT, selectedUnit.getGovernment());
            selectedUnit.getGovernment().deleteUnit(selectedUnit);
        }
        selectedMilitaryUnits = null;
        return UnitMenuMessages.SUCCESSFUL_DISBAND;
    }
}
