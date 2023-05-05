package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Moat;
import org.example.model.game.NumericalEnums;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.*;
import org.example.model.game.units.unitconstants.*;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    public static ArrayList<MilitaryUnit> selectedMilitaryUnits;

    public static UnitMenuMessages moveUnit(Coordinate destination) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.getBlockByRowAndColumn(destination).canUnitsGoHere() || map.findPath(selectedMilitaryUnits.get(0).getPosition(), destination) == null)
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.moveUnit(destination);
        return UnitMenuMessages.SUCCESSFUL_MOVE_UNIT;
    }

    public static UnitMenuMessages patrolUnit(Coordinate startPoint, Coordinate destination) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.getBlockByRowAndColumn(startPoint).canUnitsGoHere() || !map.getBlockByRowAndColumn(destination).canUnitsGoHere() ||
                map.findPath(selectedMilitaryUnits.get(0).getPosition(), startPoint) == null || map.findPath(startPoint, destination) == null)
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.patrol(startPoint, destination);
        return UnitMenuMessages.SUCCESSFUL_PATROL;
    }

    // TODO: move to game menu
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
            unit.changeHitPoint(-totalDamage);
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

    public static UnitMenuMessages assignToOilDuty(Coordinate location) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (map.getBlockByRowAndColumn(location).getBuilding().getBuildingType() != BuildingType.getBuildingTypeByName(BuildingTypeName.OIL_SMELTER))
            return UnitMenuMessages.INVALID_TARGET;
        Engineer engineer = firstEngineerSelected(true);
        if (engineer == null) return UnitMenuMessages.UNITS_CANT_POUR_OIL;
        if (engineer.hasOil()) return UnitMenuMessages.ALREADY_HAS_OIL;
        engineer.assignToBoilingOilDuty();
        engineer.moveUnit(location);
        return UnitMenuMessages.SUCCESSFUL_ASSIGN_TO_OIL_DUTY;
    }

    public static UnitMenuMessages pourOil(String direction) {
        int verticalChange = 0;
        int horizontalChange = 0;
        switch (direction) {
            case "up":
                verticalChange = -1;
                break;
            case "down":
                verticalChange = 1;
                break;
            case "left":
                horizontalChange = -1;
                break;
            case "right":
                horizontalChange = 1;
                break;
            default:
                return UnitMenuMessages.INVALID_DIRECTION;
        }

        Engineer selectedEngineer = firstEngineerSelected(false);
        if (selectedEngineer == null) return UnitMenuMessages.UNITS_CANT_POUR_OIL;
        Coordinate targetPosition = selectedEngineer.getPosition();
        targetPosition.row += verticalChange;
        targetPosition.column += horizontalChange;
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.isIndexInBounds(targetPosition)) return UnitMenuMessages.TARGET_OUT_OF_MAP;
        map.getBlockByRowAndColumn(targetPosition).setOnFire(true);

        return UnitMenuMessages.SUCCESSFUL_POUR_OIL;
    }

    public static UnitMenuMessages digMoat(Coordinate position) {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).canDigHere())
            return UnitMenuMessages.INVALID_TARGET;
        boolean canDigMoats = false;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof MilitaryPerson && ((MilitaryPersonRole) selectedMilitaryUnit.getRole()).isCanDigMoats()) {
                canDigMoats = true;
                break;
            }
        if (!canDigMoats) return UnitMenuMessages.UNITS_CANT_DIG_MOAT;
        UnitMenuMessages moveResult;
        if ((moveResult = moveUnit(position)) != UnitMenuMessages.SUCCESSFUL_MOVE_UNIT) return moveResult;
        Moat moat = new Moat(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof MilitaryPerson && ((MilitaryPersonRole) selectedMilitaryUnit.getRole()).isCanDigMoats())
                selectedMilitaryUnit.setMoatAboutToBeDug(moat);
        return UnitMenuMessages.SUCCESSFUL_DIG_MOAT;
    }

    public static UnitMenuMessages digTunnel(Coordinate position) {
        Tunneler tunneler = firstTunnelerSelected();
        if (tunneler == null) return UnitMenuMessages.INVALID_TUNNEL_UNIT;
        Block target = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position);
        if (!target.canDigHere()) return UnitMenuMessages.INVALID_TARGET;
        //TODO: select target castle and find path + add state to tunnelers
        return null;
    }

    public static UnitMenuMessages build(MilitaryEquipmentRole equipmentRole) {
        Engineer selectedEngineer = firstEngineerSelected(false);
        if (selectedEngineer == null) return UnitMenuMessages.UNITS_CANT_BUILD;
        if (equipmentRole.tryToProduceThisMany(selectedEngineer.getGovernment(), selectedEngineer.getPosition(), 1) == 0)
            return UnitMenuMessages.INSUFFICIENT_RESOURCES;
        return UnitMenuMessages.SUCCESSFUL_BUILD;
    }

    public static UnitMenuMessages disband() {
        for (MilitaryUnit selectedUnit : selectedMilitaryUnits) {
            new Unit(selectedUnit.getPosition(), RoleName.PEASANT, selectedUnit.getGovernment()).addToGovernmentAndBlock();
            selectedUnit.deleteUnitFromGovernmentAndMap();
        }
        selectedMilitaryUnits = null;
        return UnitMenuMessages.SUCCESSFUL_DISBAND;
    }

    private static Engineer firstEngineerSelected(boolean hasToBeOnOilDuty) {
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof Engineer && (!hasToBeOnOilDuty || ((Engineer) selectedMilitaryUnit).isOnBoilingDuty()))
                return (Engineer) selectedMilitaryUnit;
        return null;
    }

    private static Tunneler firstTunnelerSelected() {
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof Tunneler) return (Tunneler) selectedMilitaryUnit;
        return null;
    }
}