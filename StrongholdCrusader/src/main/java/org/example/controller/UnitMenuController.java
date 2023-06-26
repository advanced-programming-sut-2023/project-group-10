package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.*;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.*;
import org.example.model.game.units.unitconstants.*;
import org.example.view.enums.messages.UnitMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitMenuController {
    public static ArrayList<MilitaryUnit> selectedMilitaryUnits;

    public static UnitMenuMessages moveUnit(Coordinate destination) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.getBlockByRowAndColumn(destination).canUnitsGoHere(true) || map.findPath(selectedMilitaryUnits.get(0).getPosition(), destination) == null)
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.moveUnit(destination);
        return UnitMenuMessages.SUCCESSFUL_MOVE_UNIT;
    }

    public static UnitMenuMessages patrolUnit(Coordinate startPoint, Coordinate destination) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.getBlockByRowAndColumn(startPoint).canUnitsGoHere(true) || !map.getBlockByRowAndColumn(destination).canUnitsGoHere(true))
            return UnitMenuMessages.INVALID_DESTINATION;
        if (map.findPath(selectedMilitaryUnits.get(0).getPosition(), startPoint) == null || map.findPath(startPoint, destination) == null)
            return UnitMenuMessages.TOUR_BLOCKED;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.patrol(startPoint, destination);
        return UnitMenuMessages.SUCCESSFUL_PATROL;
    }

    public static UnitMenuMessages setStance(String stance) {
        MilitaryUnitStance unitStance = MilitaryUnitStance.getStanceByName(stance);
        if (unitStance == null) return UnitMenuMessages.INVALID_STANCE;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            selectedMilitaryUnit.changeStance(unitStance);
        return UnitMenuMessages.SUCCESSFUL_SET_STANCE;
    }

    public static UnitMenuMessages attackEnemy(Coordinate target) {
        int totalDamageToUnits = 0;
        int totalDamageToBuildings = 0;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits) {
            selectedMilitaryUnit.setOnPatrol(false);
            if (selectedMilitaryUnit.getPosition().getDistanceFrom(target) <= selectedMilitaryUnit.getRange()) {
                if (selectedMilitaryUnit instanceof SiegeEquipment && selectedMilitaryUnit.getRole().getName() != RoleName.FIRE_BALLISTA)
                    totalDamageToBuildings += ((MilitaryUnitRole) selectedMilitaryUnit.getRole()).getAttackRating().getValue() * NumericalEnums.DAMAGE_COEFFICIENT.getValue();
                else
                    totalDamageToUnits += ((MilitaryUnitRole) selectedMilitaryUnit.getRole()).getAttackRating().getValue() * NumericalEnums.DAMAGE_COEFFICIENT.getValue();
            }
        }
        if (totalDamageToUnits == 0 && totalDamageToBuildings == 0) return UnitMenuMessages.TARGET_OUT_OF_RANGE;
        Block targetBlock = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(target);
        ArrayList<Unit> enemyUnits = targetBlock.getAllAttackableEnemyUnits(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        Building enemyBuilding = targetBlock.getBuilding();
        if (enemyUnits.size() == 0 && enemyBuilding != null && enemyBuilding.getGovernment() == Stronghold.getCurrentBattle().getGovernmentAboutToPlay())
            return UnitMenuMessages.NO_ENEMY_HERE;
        ArrayList<Unit> deadUnits = new ArrayList<>();
        for (Unit unit : enemyUnits) {
            unit.changeHitPoint(-totalDamageToUnits);
            if (unit.isDead()) deadUnits.add(unit);
        }
        for (Unit deadUnit : deadUnits)
            deadUnit.killMe();
        if (enemyBuilding != null && enemyBuilding.getGovernment() != Stronghold.getCurrentBattle().getGovernmentAboutToPlay()) {
            enemyBuilding.changeHitPoint(-totalDamageToBuildings);
            if (enemyBuilding.getHitPoint() <= 0) enemyBuilding.deleteBuildingFromMapAndGovernment();
        }
        return UnitMenuMessages.SUCCESSFUL_ENEMY_ATTACK;
    }

    public static UnitMenuMessages airAttack(Coordinate target) {
        for (MilitaryUnit unit : selectedMilitaryUnits) {
            if (((MilitaryUnitRole) unit.getRole()).getAttackRange() == Quality.ZERO)
                return UnitMenuMessages.SELECTED_MELEE_UNIT;
        }
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
        selectedEngineer.setHasOil(false);
        selectedEngineer.moveUnit(getNearestOilSmelterCoordinate(selectedEngineer.getPosition()));
        return UnitMenuMessages.SUCCESSFUL_POUR_OIL;
    }

    private static Coordinate getNearestOilSmelterCoordinate(Coordinate position) {
        Battle battle = Stronghold.getCurrentBattle();
        Coordinate nearestOilSmelterPosition = null;
        int pathLength = Integer.MAX_VALUE;
        for (Building building : battle.getGovernmentAboutToPlay().getBuildings()) {
            if (building.getBuildingType().getName() != BuildingTypeName.OIL_SMELTER) continue;
            ArrayList<Coordinate> path = battle.getBattleMap().findPath(position, building.getPosition());
            if (path != null && path.size() < pathLength) {
                pathLength = path.size();
                nearestOilSmelterPosition = building.getPosition();
            }
        }
        return nearestOilSmelterPosition;
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

    public static UnitMenuMessages fillMoat(Coordinate position) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        Droppable moat = map.getBlockByRowAndColumn(position).getDroppable();
        if (!(moat instanceof Moat)) return UnitMenuMessages.INVALID_TARGET;
        if (!map.getBlockByRowAndColumn(position).getTexture().isWalkable() || map.findPath(selectedMilitaryUnits.get(0).getPosition(), position) == null)
            return UnitMenuMessages.INVALID_DESTINATION;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof MilitaryPerson) {
                selectedMilitaryUnit.moveUnit(position);
                selectedMilitaryUnit.setMoatAboutToBeFilled((Moat) moat);
            }
        return UnitMenuMessages.SUCCESSFUL_FILL_MOAT;
    }

    public static UnitMenuMessages digTunnel(Coordinate position) {
        Tunneler tunneler = firstTunnelerSelected();
        if (tunneler == null) return UnitMenuMessages.INVALID_TUNNEL_UNIT;
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.getBlockByRowAndColumn(position).canDigHere() || map.findPath(tunneler.getPosition(), position) == null)
            return UnitMenuMessages.INVALID_TARGET;
        Building nearestEnemyCastle = getNearestEnemyCastle(position);
        if (nearestEnemyCastle == null) return UnitMenuMessages.NO_ENEMY_CASTLE;
        tunneler.setTunnelerState(TunnelerState.GOING_TO_DIG_TUNNEL);
        tunneler.setTargetBuilding(nearestEnemyCastle);
        tunneler.moveUnit(position);
        return UnitMenuMessages.SUCCESSFUL_DIG_TUNNEL;
    }

    public static void stop() {
        for (MilitaryUnit militaryUnit : selectedMilitaryUnits) {
            militaryUnit.stop();
        }
    }

    private static Building getNearestEnemyCastle(Coordinate position) {
        Battle battle = Stronghold.getCurrentBattle();
        BuildingTypeName[] attackableBuildingTypes = {BuildingTypeName.WALL, BuildingTypeName.LOOKOUT_TOWER, BuildingTypeName.SMALL_STONE_GATEHOUSE, BuildingTypeName.LARGE_STONE_GATEHOUSE};
        Building nearestEnemyCastle = null;
        int pathLength = Integer.MAX_VALUE;

        for (Government government : battle.getGovernments()) {
            if (government == battle.getGovernmentAboutToPlay()) continue;
            for (Building building : government.getBuildings())
                if (Arrays.asList(attackableBuildingTypes).contains(building.getBuildingType().getName())) {
                    ArrayList<Coordinate> path = battle.getBattleMap().findPath(position, building.getPosition());
                    if (path == null) continue;
                    if (path.size() < pathLength) {
                        pathLength = path.size();
                        nearestEnemyCastle = building;
                    }
                }
        }
        return nearestEnemyCastle;
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
            if (selectedUnit instanceof SiegeEquipment || selectedUnit.getRole().getName() == RoleName.LORD)
                continue;
            Unit peasant = new Unit(selectedUnit.getGovernment().getKeep(), RoleName.PEASANT, selectedUnit.getGovernment());
            peasant.addToGovernmentAndBlockAndView();
            selectedUnit.killMe();
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

    public static UnitMenuMessages captureBuilding(Coordinate position) {
        MilitaryUnit selectedCapturingUnit = getSelectedCapturingUnit();
        if (selectedCapturingUnit == null) return UnitMenuMessages.NO_CAPTURING_UNITS;
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding() == null)
            return UnitMenuMessages.NO_BUILDING;
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding().getGovernment().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay()))
            return UnitMenuMessages.YOUR_OWN_BUILDING;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding().getBuildingType().isCapturable())
            return UnitMenuMessages.UNCAPTURABLE_BUILDING_TYPE;
        if (Stronghold.getCurrentBattle().getBattleMap().findPath(selectedCapturingUnit.getPosition(), position) == null)
            return UnitMenuMessages.UNREACHABLE_DESTINATION;
        selectedCapturingUnit.moveUnit(position);
        return UnitMenuMessages.GATEHOUSE_CAPTURED_SUCCESSFULLY;
    }

    private static MilitaryUnit getSelectedCapturingUnit() {
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits) {
            if (selectedMilitaryUnit instanceof SiegeTower || selectedMilitaryUnit instanceof Ladderman)
                return selectedMilitaryUnit;
        }
        return null;
    }

}