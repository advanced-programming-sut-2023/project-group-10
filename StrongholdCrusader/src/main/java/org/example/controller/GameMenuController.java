package org.example.controller;

import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
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
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.envirnmont.Node;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.SiegeEquipment;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.model.game.units.unitconstants.WorkerRole;
import org.example.view.CommonGFXActions;
import org.example.view.MountEquipmentMenu;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameMenuController {
    public static User currentPlayer() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
    }

    public static int showPopularity() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().get();
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
            if (itemIntegerEntry.getValue() < government.getCitizensCounts() * (foodRate + 2) * (0.5))
                return GameMenuMessages.INSUFFICIENT_FOOD;
        }
        government.setFoodRate(foodRate);
        return GameMenuMessages.SET_FOOD_RATE_SUCCESSFUL;
    }

    public static GameMenuMessages setTaxRate(int taxRate) {
        Government government = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        if (taxRate < -3 || taxRate > 8) return GameMenuMessages.INVALID_TAX_RATE;
        if (taxRate < 0 && government.getGold() < government.getCitizensCounts() * (0.6 + (Math.abs(taxRate) - 1)))
            return GameMenuMessages.INSUFFICIENT_GOLD;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(taxRate);
        return GameMenuMessages.SET_TAX_RATE_SUCCESSFUL;

    }

    public static GameMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || fearRate > 5) return GameMenuMessages.INVALID_FEAR_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(fearRate);
        return GameMenuMessages.SET_FEAR_RATE_SUCCESSFUL;
    }


    public static GameMenuMessages dropBuilding(Coordinate position, BuildingTypeName buildingTypeName) {
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getDroppable() != null)
            return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).isKeep())
            return GameMenuMessages.IS_KEEP;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).isBuildable())
            return GameMenuMessages.INCOMPATIBLE_LAND;
        int neededPeasants = BuildingType.getBuildingTypeByName(buildingTypeName).getEmployeeCount();
        if (neededPeasants > Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPeasantsCount())
            return GameMenuMessages.NOT_ENOUGH_PEASANTS;
        RoleName workerRole = WorkerRole.getRoleNameByWorkplace(buildingTypeName);
        if (workerRole != null) Unit.produceUnits(workerRole, neededPeasants, position);
        ArrayList<Unit> tempCopy = new ArrayList<>(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getUnits());
        if (neededPeasants != 0) {
            for (Unit unit : tempCopy)
                if (unit.getRole().getName() == RoleName.PEASANT) {
                    unit.killMe();
                    neededPeasants--;
                    if (neededPeasants == 0) break;
                }
        }
        BuildingType buildingType = BuildingType.getBuildingTypeByName(buildingTypeName);
        Government player = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        if (buildingType.getBuildingCost() > player.getGold()) return GameMenuMessages.NOT_ENOUGH_GOLD;
        for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet())
            if (player.getItemCount(entry.getKey()) < entry.getValue()) return GameMenuMessages.NOT_ENOUGH_RESOURCES;
        if (buildingType instanceof ItemProducingBuildingType) {
            if (!new ItemProducingBuilding(position, player, buildingTypeName).addToGovernmentAndBlock())
                return GameMenuMessages.INCOMPATIBLE_LAND;
        } else if (buildingTypeName == BuildingTypeName.STAIRS)
            new Stairs(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay()).addToGovernmentAndBlock();
        else
            new Building(position, Stronghold.getCurrentBattle().getGovernmentAboutToPlay(), buildingTypeName).addToGovernmentAndBlock();
        player.changeGold(-buildingType.getBuildingCost());
        for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet())
            player.changeItemCount(entry.getKey(), -entry.getValue());
        return GameMenuMessages.SUCCESSFUL_DROP;
    }

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
        if (selectedMilitaryUnits == null || selectedMilitaryUnits.size() == 0) return GameMenuMessages.NO_UNITS_FOUND;
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

    public static void initializeGame(HashMap<String, Color> colors, HashMap<String, Coordinate> keeps, org.example.model.game.envirnmont.Map map) {
        Government[] governments = new Government[colors.size()];
        int x = 0;

        for (Map.Entry<String, javafx.scene.paint.Color> player : colors.entrySet()) {
            User owner = User.getUserByUsername(player.getKey());
            Coordinate keep = keeps.get(player.getKey());
            Government gov = new Government(owner, player.getValue(), keep);
            gov.changeItemCount(Item.WOOD, 20);
            gov.changeItemCount(Item.STONE, 20);
            gov.setGold(2000);
            map.getBlockByRowAndColumn(keep).setKeep(gov);
            governments[x] = gov;
            x++;
        }

        Battle battle = new Battle(map, governments);
        Stronghold.setCurrentBattle(battle);
    }

    public static GameMenuMessages dropUnit(Coordinate position, RoleName type, int count) {
        if (count < 0) return GameMenuMessages.INVALID_UNIT_COUNT;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).canUnitsGoHere(false))
            return GameMenuMessages.UNWALKABLE_LAND;
        Unit.produceUnits(type, count, position);
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

    public static void removeAllUnits(Government government, boolean exceptLord) {
        ArrayList<Unit> tempCopy = new ArrayList<>(government.getUnits());
        for (Unit unit : tempCopy) {
            if (exceptLord && unit.getRole().getName() == RoleName.LORD) continue;
            unit.killMe();
        }
    }

    public static void removeAllBuildings(Government government) {
        ArrayList<Building> tempCopy = new ArrayList<>(government.getBuildings());
        for (Building building : tempCopy)
            building.deleteBuildingFromMapAndGovernment();
        ExtendedBlock keepBlock = Stronghold.getCurrentBattle().getBattleMap().getExtendedBlockByRowAndColumn(government.getKeep());
        keepBlock.getBlock().setKeep(null);
        keepBlock.removeBuilding();
    }

    public static void illness(Government government){
        org.example.model.game.envirnmont.Map map = Stronghold.getCurrentBattle().getBattleMap();
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        if(randomNumber != 0) return;

        int randomBlock = random.nextInt(Stronghold.getCurrentBattle().getBattleMap().getSize() - 10);
        ArrayList<ExtendedBlock> blocksAroundKeep = new ArrayList<>();
        for(int i = randomBlock; i < randomBlock + 6; i++){
            for(int j = randomBlock; j < randomBlock + 6; j++) {
                if(map.getBlockByRowAndColumn(i, j).getBuilding() != null && map.getBlockByRowAndColumn(i, j).getBuilding().getGovernment().equals(government) &&
                   map.getBlockByRowAndColumn(i, j).getBuilding().getBuildingType().getName().equals(BuildingTypeName.CATHEDRAL))
                    return;
                blocksAroundKeep.add(map.getExtendedBlockByRowAndColumn(new Coordinate(i, j)));
            }
        }

        for(ExtendedBlock extendedBlock : blocksAroundKeep){
            extendedBlock.getBlockView().setBlendMode(BlendMode.BLUE);
            extendedBlock.getBlock().setIll(true);
            extendedBlock.getBlock().setIllnessOwner(government);
        }
        government.setIll(true);
        government.setIllnessCenter(randomBlock + 3);
    }

    public static void removeIllness(Government government){
        if(!government.isIll()) return;

        org.example.model.game.envirnmont.Map map = Stronghold.getCurrentBattle().getBattleMap();
        ArrayList<ExtendedBlock> illBlocks = new ArrayList<>();
        int center = government.getIllnessCenter();
        boolean cathedralExists = false;

        for (int i = center - 3; i < center + 3; i++){
            for(int j = center - 3; j < center + 3; j++)
                illBlocks.add(map.getExtendedBlockByRowAndColumn(new Coordinate(i, j)));
        }

        for(ExtendedBlock extendedBlock : illBlocks){
            if (extendedBlock.getBlock().getBuilding() != null && extendedBlock.getBlock().getBuilding().getGovernment().equals(government) &&
                extendedBlock.getBlock().getBuilding().getBuildingType().getName().equals(BuildingTypeName.CATHEDRAL))
                cathedralExists = true;
        }

        if(cathedralExists){
            for(ExtendedBlock extendedBlock : illBlocks){
                extendedBlock.getBlock().setIll(false);
                extendedBlock.getBlockView().setBlendMode(null);
                extendedBlock.getBlock().setIllnessOwner(null);
            }
            government.setIll(false);
        }
    }

    public static GameMenuMessages goToNextPlayer() {
        Government currentGovernment = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government == null) continue;
            produceItems(government);
        }

        if(currentGovernment!=null) {
            moveAllUnits(currentGovernment);
            attackAllUnits(currentGovernment);

            collectTaxes(currentGovernment);
            feedCitizens(currentGovernment);
            addPeasants(currentGovernment);
            currentGovernment.setExcessFood(0);
            updateFoodCount(currentGovernment);
            updatePopularity(currentGovernment);
        }
        removeIllness(currentGovernment);
        Government dead;
        while ((dead = deadLord()) != null) {
            countScore(dead);
            removeAllBuildings(dead);
            removeAllUnits(dead, false);
            Stronghold.getCurrentBattle().removeGovernment(dead);
        }
        if (aliveLords().size() == 1) {
            countScore(aliveLords().get(0).getGovernment());
            return GameMenuMessages.GAME_OVER;
        }
        Stronghold.getCurrentBattle().goToNextPlayer();
        illness(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
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
                government.changeItemCount(itemIntegerEntry.getKey(), -government.getCitizensCounts() * (government.getFoodRate() + 2) * (0.5));
            }
        }
    }

    private static void updatePopularity(Government government) {
        modifyFoodRate(government);
        modifyTaxRate(government);
        religion(government);
        fear(government);
        if(government.isIll()) government.getPopularity().set(government.getPopularity().intValue() - 5);
    }

    private static void modifyFoodRate(Government government) {
        outer:
        while (government.getFoodRate() > -2) {
            for (Map.Entry<Item, Double> itemIntegerEntry : government.getFoodList().entrySet()) {
                if (itemIntegerEntry.getValue() < government.getCitizensCounts() * (government.getFoodRate() + 2) * (0.5)) {
                    government.setFoodRate(government.getFoodRate() - 1);
                } else break outer;
            }
        }
        government.changePopularity(government.getFoodRate(), "Food");
    }

    private static void modifyTaxRate(Government government) {
        while (government.getTaxRate() < 0) {
            if (government.getGold() < government.getTaxRate() * government.getCitizensCounts())
                government.setTaxRate(government.getTaxRate() + 1);
            else break;
        }
        government.changePopularity(-government.getTaxRate(), "Tax");

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
        government.changeGold(government.getCitizensCounts() * government.calculateTax());
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
        Building building;
        int lastIndex = (movesLeft = Math.min(moveCount, path.size())) - 1;
        CommonGFXActions.getMoveAnimation(unit.getRole(), path.subList(0, lastIndex + 1), unit.getPosition(), unit.getBodyGraphics()).play();
        unit.setPosition(path.get(lastIndex));
        for (int i = 0; i < movesLeft - 1; i++) {
            building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(path.get(i)).getBuilding();
            if (building != null && building.getBuildingType().getName() == BuildingTypeName.KILLING_PIT) {
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
            if (unit instanceof SiegeEquipment && unit.getRole().getName() != RoleName.FIRE_BALLISTA) continue;
            if ((damage = ((MilitaryUnitRole) unit.getRole()).getAttackRating().getValue() * NumericalEnums.DAMAGE_COEFFICIENT.getValue()) == 0)
                continue;

            closestEnemyUnit = findClosestEnemyUnit(unit.getPosition());
            if (closestEnemyUnit == null) continue;
            if (closestEnemyUnit.getPosition().getDistanceFrom(unit.getPosition()) <= ((MilitaryUnit) unit).getRange()) {
                CommonGFXActions.getAttackAnimation(unit.getRole(), ((MilitaryUnit) unit).getBodyGraphics(), unit.getPosition(), closestEnemyUnit.getPosition()).play();
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
                        CommonGFXActions.getMoveAnimation(unit.getRole(), path.subList(0, i + 1), unit.getPosition(), ((MilitaryUnit) unit).getBodyGraphics()).play();
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
        capacity -= government.getPeasantsCount();
        if (capacity < newPeasants) newPeasants = capacity;
        for (int i = 0; i < newPeasants; i++) {
            government.addUnit(new Unit(government.getKeep(), RoleName.PEASANT, government));

        }
    }

    public static void countScore(Government government) {
        government.getOwner().setHighScore(Stronghold.getCurrentBattle().getTurnsPassed() * NumericalEnums.SCORE_CONSTANT.getValue());
    }

    private static Government deadLord() {
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government != null && government.getLord().isDead()) return government;
        }
        return null;
    }

    private static ArrayList<Unit> aliveLords() {
        ArrayList<Unit> lords = new ArrayList<>();
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            if (government != null && !government.getLord().isDead()) lords.add(government.getLord());
        }
        return lords;
    }
}