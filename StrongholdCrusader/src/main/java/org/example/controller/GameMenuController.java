package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Node;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.view.enums.messages.GameMenuMessages;

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

        for (Map.Entry<Item, Integer> list : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodList().entrySet()) {
            foodList = foodList.concat("Food : " + list.getKey() + " Amount : " + list.getValue() + "\n");
        }
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

    //TODO: I gave the modified rate to gov/ change it
    public static GameMenuMessages setFoodRate(int foodRate) {
        if (foodRate < -2 || foodRate > 2) return GameMenuMessages.INVALID_FOOD_RATE;
        //TODO: feed people by foodRate+2
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFoodRate(foodRate);
        //TODO: affect it on popularity!
        return GameMenuMessages.SET_FOOD_RATE_SUCCESSFUL;

    }

    public static GameMenuMessages setTaxRate(int taxRate) {
        if (taxRate < -3 || taxRate > 8) return GameMenuMessages.INVALID_TAX_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(taxRate);
        return GameMenuMessages.SET_FOOD_TAX_SUCCESSFUL;

    }

    public static GameMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || fearRate > 5) return GameMenuMessages.INVALID_FEAR_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(fearRate);
        return GameMenuMessages.SET_FOOD_TAX_SUCCESSFUL;
    }


    public static GameMenuMessages dropBuilding(Coordinate position, String type) {
        if (BuildingTypeName.getBuildingTypeNameByNameString(type) == null)
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getTexture().isBuildable())
            return GameMenuMessages.INCOMPATIBLE_LAND;
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding() != null)
            return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).setDroppable(new Building(position,
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay(), BuildingTypeName.getBuildingTypeNameByNameString(type)));
        return GameMenuMessages.SUCCESSFUL_DROP;
    }

    // TODO: add this to the menu
    public static int showRoundsPlayed() {
        return Stronghold.getCurrentBattle().getTurnsPassed();
    }

    private static void showCurrentPlayer() {
        User player = GameMenuController.currentPlayer();
        System.out.println("player \" " + player.getNickname() + "\" with username : " + player.getUsername() + "is about to play!");
    }

    public static GameMenuMessages selectBuilding(Coordinate position) {
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getBuilding() == null)
            return GameMenuMessages.EMPTY_LAND;

        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).
                getBuilding().getGovernment().getOwner().getUsername().
                equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername()))
            return GameMenuMessages.OPPONENT_BUILDING;

        return GameMenuMessages.SUCCESSFUL_SELECT;
    }

    public static GameMenuMessages selectUnit(Coordinate position) {
        if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(position).getMilitaryUnitsByGovernment(
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay()) == null)
            return GameMenuMessages.NO_UNITS_FOUND;
        UnitMenuController.selectedMilitaryUnits.clear();
        UnitMenuController.selectedMilitaryUnits = Stronghold.getCurrentBattle().getBattleMap()
                .getBlockByRowAndColumn(position).getMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        return GameMenuMessages.SUCCESSFUL_SELECT;
    }


    //TODO: what's this?
    public static void initializeGame(HashMap<String, String> players, org.example.model.game.envirnmont.Map map) {

    }


    private void moveAllUnits(Government government) {
        for (Unit unit : government.getUnits())
            if (unit instanceof MilitaryUnit && ((MilitaryUnit) unit).getDestination() != null)
                moveUnit((MilitaryUnit) unit, unit.getSpeed());
    }

    private void moveUnit(MilitaryUnit unit, int moveCount) {
        ArrayList<Coordinate> path = Stronghold.getCurrentBattle().getBattleMap().findPath(new Node(unit.getPosition()), new Node(unit.getDestination()));
        if (path == null) return;
        int movesLeft;
        unit.setPosition(path.get((movesLeft = Math.min(moveCount, path.size())) - 1));
        if (unit.getDestination().equals(unit.getPosition())) unit.updateDestination();
        if (!unit.isOnPatrol()) return;
        moveCount = moveCount - movesLeft;
        if (moveCount > 0) moveUnit(unit, moveCount);
    }


    //Can some part of land be owned by someone?
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

    public void goToNextPlayer() {
        Government currentGovernment = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        moveAllUnits(currentGovernment);
        attackAllUnits(currentGovernment);
        //farms and etc. should produce at every turn(if their rates matches)
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            produceItems(government);
        }
        collectTaxes(currentGovernment);
        producePeasants(currentGovernment);
        updateFoodCount(currentGovernment);
        updatePopularity(currentGovernment);
        Stronghold.getCurrentBattle().goToNextPlayer();
    }

    private void produceItems(Government government) {
        for (Building building : government.getBuildings()) {
            if (building instanceof ItemProducingBuilding) {
                ((ItemProducingBuilding) building).produce();
            }
        }
    }


    private void producePeasants(Government government) {
        for (Building building : government.getBuildings()) {
            if (building instanceof ItemProducingBuilding) {
                ((ItemProducingBuilding) building).produce();
            }
        }
    }

    private void updateFoodCount(Government government) {
        for (Map.Entry<Item, Integer> itemIntegerEntry : government.getItemList().entrySet()) {
            if (itemIntegerEntry.getValue() != 0 && itemIntegerEntry.getKey().isFood()) {
                //TODO
            }
        }
    }

    private void updatePopularity(Government government) {

    }

    private void collectTaxes(Government government) {

    }

    private void attackAllUnits(Government government) {

    }


}
