package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Moat;
import org.example.model.game.NumericalEnums;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.Engineer;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.*;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    public static ArrayList<MilitaryUnit> selectedMilitaryUnits;

    public static UnitMenuMessages moveUnit(Coordinate destination) {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination.row, destination.column).canUnitsGoHere())
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.moveUnit(destination);
        return UnitMenuMessages.SUCCESSFUL_MOVE_UNIT;
    }

    public static UnitMenuMessages patrolUnit(Coordinate startPoint, Coordinate destination) {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(startPoint).canUnitsGoHere() ||
                !Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination).canUnitsGoHere())
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.patrol(startPoint, destination);
        return UnitMenuMessages.SUCCESSFUL_PATROL;
    }

    public static UnitMenuMessages setStance(Coordinate position, MilitaryUnitStance stance) {
        //TODO: check and complete doc:pg.23
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.changeStance(stance);
        return UnitMenuMessages.SUCCESSFUL_SET_STANCE;
    }

    public static UnitMenuMessages attackEnemy(Coordinate target) {
        int totalDamage = 0;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits) {
            selectedMilitaryUnit.setOnPatrol(false);
            if (selectedMilitaryUnit.getPosition().getDistanceFrom(target) <=
                    (((MilitaryUnitRole) selectedMilitaryUnit.getRole()).getAttackRange().getValue() + selectedMilitaryUnit.getBoostInFireRange()) * NumericalEnums.RANGE_COEFFICIENT.getValue())
                totalDamage += ((MilitaryUnitRole) selectedMilitaryUnit.getRole()).getAttackRating().getValue() * NumericalEnums.DAMAGE_COEFFICIENT.getValue();
        }
        if (totalDamage == 0) return UnitMenuMessages.TARGET_OUT_OF_RANGE;
        Block targetBlock = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(target);
        if (targetBlock.getAllUnits().size() == 0) return UnitMenuMessages.NO_ENEMY_HERE;
        ArrayList<Unit> deadUnits = new ArrayList<>();
        for (Unit unit : targetBlock.getAllUnits()) {
            if (unit.getGovernment().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay())) continue;
            unit.setHitPoint(unit.getHitPoint() - totalDamage);
            if (unit.isDead()) deadUnits.add(unit);
        }
        for (Unit deadUnit : deadUnits) {
            deadUnit.getGovernment().deleteUnit(deadUnit);
            targetBlock.removeUnit(deadUnit);
        }
        return UnitMenuMessages.SUCCESSFUL_ENEMY_ATTACK;
    }

    public static UnitMenuMessages airAttack(Coordinate target) {
        //TODO: difference between air attack and enemy attack?
        return attackEnemy(target);
    }

    public static UnitMenuMessages pourOil(String direction) {

        return null;
    }

    public static UnitMenuMessages digMoat(Coordinate position) {
        UnitMenuMessages moveResult;
        if ((moveResult = moveUnit(position)) != UnitMenuMessages.SUCCESSFUL_MOVE_UNIT) return moveResult;
        boolean canDigMoats = false;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof MilitaryPerson && ((MilitaryPersonRole) selectedMilitaryUnit.getRole()).isCanDigMoats()) {
                canDigMoats = true;
                break;
            }
        if (!canDigMoats) return UnitMenuMessages.UNITS_CANT_DIG_MOAT;
        //TODO: wait until unit get to the target then add moat to map
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).setDroppable(new Moat(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay()));
        return UnitMenuMessages.SUCCESSFUL_DIG_MOAT;
    }

    public static UnitMenuMessages build(MilitaryEquipmentRole equipmentRole) {
        Engineer firstEngineerSelected = null;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits) {
            if (selectedMilitaryUnit instanceof Engineer) {
                firstEngineerSelected = (Engineer) selectedMilitaryUnit;
                break;
            }
        }
        if (firstEngineerSelected == null) return UnitMenuMessages.UNITS_CANT_BUILD;
        if (equipmentRole.tryToProduceThisMany(firstEngineerSelected.getGovernment(), firstEngineerSelected.getPosition(), 1) == 0)
            return UnitMenuMessages.INSUFFICIENT_RESOURCES;
        return UnitMenuMessages.SUCCESSFUL_BUILD;
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