package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.*;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.Stairs;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.buildings.buildingconstants.PopularityIncreasingBuildingType;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Node;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.SiegeEquipment;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.*;
import org.example.view.CustomizeMapMenu;
import org.example.view.enums.messages.GameMenuMessages;
import org.example.view.enums.messages.MountEquipmentMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMenuController {
    public static User currentPlayer() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
    }

    public static int showPopularity() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity();
    }

    public static String showPopularityFactors() {
        String show = "";
        HashMap<String, Integer> popularityFactors = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularityFactors();
        for (Map.Entry<String, Integer> factors : popularityFactors.entrySet()) {
            show = show.concat(factors.getKey() + " : " + factors.getValue() + "\n");
        }
        return show;
    }

    public static String showFoodList() {
        String foodList = "";
        for (Map.Entry<Item, Double> list : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodList().entrySet()) {
            foodList = foodList.concat("Food : " + list.getKey() + " Amount : " + list.getValue() + "\n");
        }
        if (foodList.isEmpty()) foodList = "You have no food!";
        return foodList;
    }

    public static int showTaxRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate();
    }

    public static int showFoodRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate();
    }

    public static int showFearRate() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate();
    }


    public static GameMenuMessages setFoodRate(int foodRate) {
        Government government = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        if (foodRate < -2 || foodRate > 2) return GameMenuMessages.INVALID_FOOD_RATE;
        for (Map.Entry<Item, Double> itemIntegerEntry : government.getFoodList().entrySet()) {
            if (itemIntegerEntry.getValue() < government.getCitizens() * (foodRate + 2) * (0.5))
                return GameMenuMessages.INSUFFICIENT_FOOD;
        }
        government.setFoodRate(foodRate);
        return GameMenuMessages.SET_FOOD_RATE_SUCCESSFUL;
    }

    public static GameMenuMessages setTaxRate(int taxRate) {
        Government government = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        if (taxRate < -3 || taxRate > 8) return GameMenuMessages.INVALID_TAX_RATE;
        if (taxRate < 0 && government.getGold() < government.getCitizens() * (0.6 + (Math.abs(taxRate) - 1)))
            return GameMenuMessages.INSUFFICIENT_GOLD;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(taxRate);
        return GameMenuMessages.SET_TAX_RATE_SUCCESSFUL;

    }

    public static GameMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || fearRate > 5) return GameMenuMessages.INVALID_FEAR_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(fearRate);
        return GameMenuMessages.SET_FEAR_RATE_SUCCESSFUL;
    }


    public static GameMenuMessages dropBuilding(Coordinate position, String type) {
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getDroppable() != null)
            return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        if(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).isKeep())
            return GameMenuMessages.IS_KEEP;
        if (BuildingTypeName.getBuildingTypeNameByNameString(type) == null)
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).isBuildable())
            return GameMenuMessages.INCOMPATIBLE_LAND;
        BuildingTypeName buildingType = BuildingTypeName.getBuildingTypeNameByNameString(type);
        int neededPeasants = BuildingType.getBuildingTypeByName(buildingType).getEmployeeCount();
        if (neededPeasants > Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPeasant())
            return GameMenuMessages.NOT_ENOUGH_PEASANTS;
        RoleName workerRole = WorkerRole.getRoleNameByWorkplace(BuildingTypeName.getBuildingTypeNameByNameString(type));
        if (workerRole != null)
            Unit.produceUnits(workerRole, neededPeasants, position);
        for (Unit unit : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getUnits())
            if (unit.getRole().getName() == RoleName.PEASANT) {
                unit.killMe();
                neededPeasants--;
                if (neededPeasants == 0) break;
            }
        if (BuildingType.getBuildingTypeByName(buildingType) instanceof ItemProducingBuildingType)
            new ItemProducingBuilding(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay(), buildingType).addToGovernmentAndBlock();
        else if (buildingType == BuildingTypeName.STAIRS)
            new Stairs(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay()).addToGovernmentAndBlock();
        else
            new Building(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay(), buildingType).addToGovernmentAndBlock();
        return GameMenuMessages.SUCCESSFUL_DROP;
    }

    // TODO: add this to the menu
    public static int showRoundsPlayed() {
        return Stronghold.getCurrentBattle().getTurnsPassed();
    }

    public static String showCurrentPlayer() {
        User player = GameMenuController.currentPlayer();
        return ("player \"" + player.getNickname() + "\" with username : " + player.getUsername() + " is about to play!");
    }

    public static GameMenuMessages selectBuilding(Coordinate position) {
        Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding();
        if (building == null) return GameMenuMessages.EMPTY_LAND;

        if (building.getGovernment() != Stronghold.getCurrentBattle().getGovernmentAboutToPlay())
            return GameMenuMessages.OPPONENT_BUILDING;

        return GameMenuMessages.SUCCESSFUL_SELECT;
    }

    public static GameMenuMessages selectUnit(Coordinate position) {
        ArrayList<MilitaryUnit> selectedMilitaryUnits = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getSelectableMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        if (selectedMilitaryUnits == null) return GameMenuMessages.NO_UNITS_FOUND;
        UnitMenuController.selectedMilitaryUnits = selectedMilitaryUnits;
        return GameMenuMessages.SUCCESSFUL_SELECT;
    }

    public static GameMenuMessages mountEquipment(Coordinate position) {
        ArrayList<MilitaryUnit> selectedMilitaryUnits = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        SiegeEquipment siegeEquipment = null;
        for (MilitaryUnit selectedMilitaryUnit : selectedMilitaryUnits)
            if (selectedMilitaryUnit instanceof SiegeEquipment) {
                siegeEquipment = (SiegeEquipment) selectedMilitaryUnit;
                break;
            }
        if (siegeEquipment == null) return GameMenuMessages.NO_EQUIPMENT_FOUND;
        MountEquipmentMenu.run(siegeEquipment);
        return GameMenuMessages.MOUNT_SUCCESSFUL;
    }

    public static void initializeGame(HashMap<String, String> players, HashMap<String, Coordinate> keeps, org.example.model.game.envirnmont.Map map) {
        Government[] governments = new Government[players.size()];
        int x = 0;

        for (Map.Entry<String, String> player : players.entrySet()) {
            User owner = User.getUserByUsername(player.getKey());
            Color color = Color.getColorByName(player.getValue());
            Coordinate keep = keeps.get(player.getKey());
            Government gov = new Government(owner, color, keep);
            gov.addItem(Item.WOOD, 20);
            gov.addItem(Item.STONE, 20);
            gov.setGold(20);
            map.getBlockByRowAndColumn(keep).setKeep(true);
            governments[x] = gov;
            x++;
        }

        Battle battle = new Battle(map, governments);
        Stronghold.setCurrentBattle(battle);

        for (Government government : governments) {
            new MilitaryPerson(government.getKeep(), RoleName.LORD, government).addToGovernmentAndBlock();
        }
        CustomizeMapMenu.run();
    }

    public static GameMenuMessages dropUnit(Coordinate position, String type, int count) {
        if (Role.getRoleByName(RoleName.getRoleNameByNameString(type)) == null)
            return GameMenuMessages.INVALID_UNIT_TYPE;

        if (count < 0) return GameMenuMessages.INVALID_UNIT_COUNT;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).canUnitsGoHere(false))
            return GameMenuMessages.UNWALKABLE_LAND;
        for (Unit producedUnit : Unit.produceUnits(RoleName.getRoleNameByNameString(type), count, position)) {
            producedUnit.addToGovernmentAndBlock();
        }
        return GameMenuMessages.SUCCESSFUL_DROP;
    }

    public static void clearForces(Coordinate destination) {
        Block target = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination);
        target.clearForcesOfGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
    }

    public static GameMenuMessages deleteStructure(Coordinate destination) {
        Block target = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(destination);
        Droppable droppable = target.getDroppable();
        if (droppable == null) return GameMenuMessages.NO_STRUCTURE;
        if (!(droppable instanceof Entity)) return GameMenuMessages.NOT_YOUR_STRUCTURE;
        if (droppable instanceof Building) ((Building) droppable).deleteBuildingFromMapAndGovernment();
        else target.setDroppable(null);
        return GameMenuMessages.SUCCESSFUL_DELETE_STRUCTURE;
    }

    private static void removeAllUnits(Government government) {
        for (int i = 0; i < Stronghold.getCurrentBattle().getBattleMap().getSize(); i++) {
            for (int j = 0; j < Stronghold.getCurrentBattle().getBattleMap().getSize(); j++) {
                if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i, j).getAllUnits() != null) {
                    ArrayList<Unit> units = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i, j).getAllUnits();
                    for (Unit unit : units) {
                        if (unit.getGovernment().equals(government))
                            Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i, j).removeUnit(unit);

                    }
                }
            }
        }
    }

    private static void removeAllBuildings(Government government) {
        for (int i = 0; i < Stronghold.getCurrentBattle().getBattleMap().getSize(); i++) {
            for (int j = 0; j < Stronghold.getCurrentBattle().getBattleMap().getSize(); j++) {
                Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i, j).getBuilding();
                if (building != null && building.getGovernment().equals(government))
                    Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i, j).setDroppable(null);
            }
        }
    }

    public static GameMenuMessages goToNextPlayer() {
        Government currentGovernment = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            produceItems(government);
        }

        moveAllUnits(currentGovernment);
        attackAllUnits(currentGovernment);

        collectTaxes(currentGovernment);
        feedCitizens(currentGovernment);
        addPeasants(currentGovernment);
        updateFoodCount(currentGovernment);
        updatePopularity(currentGovernment);
        Government dead;
        while ((dead = deadLord()) != null) {
            countScore(dead);
            removeAllBuildings(dead);
            removeAllUnits(dead);
            Stronghold.getCurrentBattle().removeGovernment(dead);
        }
        if (aliveLords().size() == 1) {
            countScore(aliveLords().get(0).getGovernment());
            return GameMenuMessages.GAME_OVER;
        }
        Stronghold.getCurrentBattle().goToNextPlayer();
        return GameMenuMessages.NEXT_PLAYER;
    }

    private static void produceItems(Government government) {
        for (Building building : government.getBuildings()) {
            if (building instanceof ItemProducingBuilding) {
                ((ItemProducingBuilding) building).produce();
            }
        }
    }

    private static void updateFoodCount(Government government) {
        for (Map.Entry<Item, Double> itemIntegerEntry : government.getItemList().entrySet()) {
            if (itemIntegerEntry.getValue() != 0 && itemIntegerEntry.getKey().isFood()) {
                government.changeItemCount(itemIntegerEntry.getKey(), government.getCitizens() * (government.getFoodRate() + 2) * (0.5));
            }
        }
    }

    private static void updatePopularity(Government government) {
        modifyFoodRate(government);
        modifyTaxRate(government);
        religion(government);
        fear(government);

    }

    private static void modifyFoodRate(Government government) {
        outer:
        while (government.getFoodRate() > -2) {
            for (Map.Entry<Item, Double> itemIntegerEntry : government.getFoodList().entrySet()) {
                if (itemIntegerEntry.getValue() < government.getCitizens() * (government.getFoodRate() + 2) * (0.5)) {
                    government.setFoodRate(government.getFoodRate() - 1);
                    break;
                } else break outer;
            }
        }
        government.changePopularity(government.getFearRate(), "Food");
    }

    private static void modifyTaxRate(Government government) {
        while (government.getTaxRate() < 0) {
            if (government.getGold() < government.getTaxRate() * government.getCitizens())
                government.setTaxRate(government.getTaxRate() + 1);
            else break;
        }
        government.changePopularity(government.getTaxRate(), "Tax");

    }

    private static void religion(Government government) {
        int religionFactor = 0;
        for (Building building : government.getBuildings()) {
            if (building.getBuildingType() instanceof PopularityIncreasingBuildingType)
                religionFactor += ((PopularityIncreasingBuildingType) building.getBuildingType()).getIncreaseInPopularity();
        }
        government.changePopularity(religionFactor, "Religion");

    }

    private static void fear(Government government) {
        government.changePopularity(government.getFearRate(), "Fear");
    }

    private static void collectTaxes(Government government) {
        government.changeGold(government.getCitizens() * government.calculateTax());
    }

    private static void feedCitizens(Government government) {
        HashMap<Item, Double> foodList = government.getFoodList();
        for (Map.Entry<Item, Double> itemDoubleEntry : foodList.entrySet()) {
            government.changeItemCount(itemDoubleEntry.getKey(), (-1) * (government.getFoodRate() + 2) * 0.5);
        }
    }

    private static void moveAllUnits(Government government) {
        for (Unit unit : government.getUnits())
            if (unit instanceof MilitaryUnit && ((MilitaryUnit) unit).getDestination() != null)
                moveUnit((MilitaryUnit) unit, unit.getSpeed());
    }

    private static void moveUnit(MilitaryUnit unit, int moveCount) {
        ArrayList<Coordinate> path = Stronghold.getCurrentBattle().getBattleMap().findPath(new Node(unit.getPosition()), new Node(unit.getDestination()));
        if (path == null) return;
        int movesLeft;
        unit.setPosition(path.get((movesLeft = Math.min(moveCount, path.size())) - 1));
        for (int i = 0; i < movesLeft - 1; i++) {
            if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(path.get(i)).getBuilding().getBuildingType().getName() == BuildingTypeName.KILLING_PIT) {
                unit.killMe();
                return;
            }
        }
        if (unit.getDestination().equals(unit.getPosition())) unit.updateDestination();
        if (!unit.isOnPatrol()) return;
        moveCount = moveCount - movesLeft;
        if (moveCount > 0) moveUnit(unit, moveCount);
    }

    private static void attackAllUnits(Government government) {
        int damage;
        Unit closestEnemyUnit;
        int pathLimit;
        ArrayList<Coordinate> path;
        for (Unit unit : government.getUnits()) {
            if (!(unit instanceof MilitaryUnit)) continue;
            if (((MilitaryUnit) unit).getDestination() != null) continue;
            if ((damage = ((MilitaryUnitRole) unit.getRole()).getAttackRating().getValue() * NumericalEnums.DAMAGE_COEFFICIENT.getValue()) == 0)
                continue;

            closestEnemyUnit = findClosestEnemyUnit(unit.getPosition());
            if (closestEnemyUnit == null) continue;
            if (closestEnemyUnit.getPosition().getDistanceFrom(unit.getPosition()) <= ((MilitaryUnit) unit).getRange()) {
                closestEnemyUnit.changeHitPoint(-damage);
                if (closestEnemyUnit.isDead()) closestEnemyUnit.killMe();
            } else if (((MilitaryUnit) unit).getStance() != MilitaryUnitStance.STAND_GROUND && ((MilitaryUnit) unit).getBoostInFireRange() == 0) {
                closestEnemyUnit = findClosestEnemyUnitBasedOnPath(unit.getPosition());
                path = Stronghold.getCurrentBattle().getBattleMap().findPath(closestEnemyUnit.getPosition(), unit.getPosition());
                if (((MilitaryUnit) unit).getStance() == MilitaryUnitStance.DEFENSIVE_STANCE)
                    pathLimit = Math.min(unit.getSpeed() / 2, path.size());
                else pathLimit = Math.min(unit.getSpeed(), path.size());
                for (int i = 0; i < pathLimit; i++)
                    if (path.get(i).getDistanceFrom(closestEnemyUnit.getPosition()) <= ((MilitaryUnit) unit).getRange()) {
                        unit.setPosition(path.get(i));
                        closestEnemyUnit.changeHitPoint(damage);
                        if (closestEnemyUnit.isDead()) closestEnemyUnit.killMe();
                    }
            }
        }
    }

    private static Unit findClosestEnemyUnitBasedOnPath(Coordinate position) {
        ArrayList<Unit> enemyUnits = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getAllAttackableEnemyUnits(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        int minDistance = Integer.MAX_VALUE;
        Unit closestEnemyUnit = null;
        ArrayList<Coordinate> path;
        for (Unit enemyUnit : enemyUnits) {
            path = Stronghold.getCurrentBattle().getBattleMap().findPath(enemyUnit.getPosition(), position);
            if (path != null && path.size() < minDistance) {
                minDistance = path.size();
                closestEnemyUnit = enemyUnit;
            }
        }
        return closestEnemyUnit;
    }

    private static Unit findClosestEnemyUnit(Coordinate position) {
        ArrayList<Unit> enemyUnits = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getAllAttackableEnemyUnits(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        int minDistance = Integer.MAX_VALUE;
        Unit closestEnemyUnit = null;
        for (Unit enemyUnit : enemyUnits) {
            if (enemyUnit.getPosition().getDistanceFrom(position) < minDistance) {
                minDistance = enemyUnit.getPosition().getDistanceFrom(position);
                closestEnemyUnit = enemyUnit;
            }
        }
        return closestEnemyUnit;
    }


    private static void addPeasants(Government government) {
        int newPeasants = government.getExcessFood() / NumericalEnums.PEASANT_PRODUCTION_RATE.getValue();
        int capacity = 0;
        for (Building building : government.getBuildings()) {
            switch (building.getBuildingType().getName()) {
                case LARGE_STONE_GATEHOUSE:
                    capacity += 10;
                    break;
                case SMALL_STONE_GATEHOUSE:
                case HOVEL:
                    capacity += 8;
                    break;
                default:
                    break;

            }
        }
        if (capacity < newPeasants)
            newPeasants = capacity;
        for (int i = 0; i < newPeasants; i++) {
            government.addUnit(new Unit(government.getKeep(), RoleName.PEASANT, government));

        }
    }

    private static void countScore(Government government) {
        government.getOwner().setHighScore(Stronghold.getCurrentBattle().getTurnsPassed() * NumericalEnums.SCORE_CONSTANT.getValue());
    }

    private static Government deadLord() {
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government.getLord().isDead()) return government;
        }
        return null;
    }

    private static ArrayList<Unit> aliveLords() {
        ArrayList<Unit> lords = new ArrayList<>();
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (!government.getLord().isDead()) lords.add(government.getLord());
        }
        return lords;
    }
}