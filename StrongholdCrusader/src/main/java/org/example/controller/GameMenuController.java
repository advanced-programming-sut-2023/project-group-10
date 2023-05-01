package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.*;

public class GameMenuController {
    public static User currentPlayer() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
    }

    public static String showPopularityFactors() {
        String show = "";
        HashMap<String, Integer> popularityFactors = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularityFactors();
        for (Map.Entry<String, Integer> factors : popularityFactors.entrySet()) {
            show = show.concat(factors.getKey() + " : " + factors.getValue() + "\n");
        }
        return show;
    }

    // TODO: add this to the menu
    public static int roundsPlayed() {
        return Stronghold.getCurrentBattle().getTurnsPassed();
    }

    public static int showPopularity() {
        return Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity();
    }

    public static String showFoodList() {
        String foodList = "";

        for (Map.Entry<Item, Integer> list : Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodList().entrySet()) {
            foodList = foodList.concat("Food : " + list.getKey() + " Amount : " + list.getValue() + "\n");
        }
        return foodList;
    }

    //ATTENTION: I gave the modified rate to gov
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
        if (fearRate < -5 || fearRate > 5) return GameMenuMessages.INVALID_TAX_RATE;
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(fearRate);
        return GameMenuMessages.SET_FOOD_TAX_SUCCESSFUL;
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


    public static GameMenuMessages dropBuilding(Coordinate position, String type) {
        //TODO: check validity of building type
        if (false) return GameMenuMessages.INVALID_BUILDING_TYPE;
        //TODO: check droppable
        if (true) return GameMenuMessages.INCOMPATIBLE_LAND;
        if (true) return GameMenuMessages.BUILDING_EXISTS_IN_THE_BLOCK;

        return GameMenuMessages.SUCCESSFUL_DROP;
    }

    //
    public static GameMenuMessages selectBuilding(Coordinate position) {
        //TODO: check droppable
        if (true) return GameMenuMessages.OPPONENT_BUILDING;
        if (true) return GameMenuMessages.EMPTY_LAND;


        return GameMenuMessages.SUCCESSFUL_SELECT;
    }

    public static GameMenuMessages selectUnit(Coordinate position) {
        return null;
    }

    public static GameMenuMessages endTurn() {
        return null;
    }

    private static void showCurrentPlayer() {
        User player = GameMenuController.currentPlayer();
        System.out.println("player \" " + player.getNickname() + "\" with username : " + player.getUsername() + "is about to play!");
    }

    public static GameMenuMessages leaveGame() {

        return null;
    }

    public static void applyChanges() {
    }

    public static void initializeGame(HashMap<String, String> players, org.example.model.game.envirnmont.Map map) {
    }

    public void goToNextPlayer() {
        Government currentGovernment = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        moveAllUnits(currentGovernment);
        attackAllUnits(currentGovernment);
        produceItems(currentGovernment);
        collectTaxes(currentGovernment);
        producePeasants(currentGovernment);
        updateFoodCount(currentGovernment);
        updatePopularity(currentGovernment);
        Stronghold.getCurrentBattle().goToNextPlayer();
    }

    private void moveAllUnits(Government government) {
        for (Unit unit : government.getUnits())
            if (unit instanceof MilitaryUnit && ((MilitaryUnit) unit).getDestination() != null)
                moveUnit((MilitaryUnit) unit, unit.getSpeed());
    }

    private void moveUnit(MilitaryUnit unit, int moveCount) {
        ArrayList<Coordinate> path = findPath(new Node(unit.getPosition()), new Node(unit.getDestination()));
        if (path==null) return;
        int movesLeft;
        unit.setPosition(path.get((movesLeft = Math.min(moveCount, path.size())) - 1));
        if (unit.getDestination().equals(unit.getPosition())) unit.updateDestination();
        if (!unit.isOnPatrol()) return;
        moveCount = moveCount - movesLeft;
        if (moveCount > 0) moveUnit(unit, moveCount);
    }

    private static ArrayList<Coordinate> findPath(Node start, Node end) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(start);
        start.visited = true;

        Node currentNode;
        while (!queue.isEmpty()) {
            currentNode = queue.pollFirst();
            for (Node neighbor : currentNode.getNeighbors(Stronghold.getCurrentBattle().getBattleMap())) {
                if (neighbor.visited) continue;
                neighbor.visited = true;
                queue.add(neighbor);
                neighbor.previousNode = currentNode;
                if (neighbor == end) {
                    queue.clear();
                    break;
                }
            }
        }

        return traceRoute(end);
    }

    private static ArrayList<Coordinate> traceRoute(Node end) {
        if (end.previousNode == null) return null;
        ArrayList<Coordinate> path = new ArrayList<>();
        Node node = end;
        while (node.previousNode != null) {
            path.add(node.coordinate);
            node = node.previousNode;
        }
        Collections.reverse(path);
        if (path.size() == 0) return null;
        return path;
    }

    private void attackAllUnits(Government government) {

    }

    private void produceItems(Government government) {
    }

    private void collectTaxes(Government government) {
    }

    private void producePeasants(Government government) {
    }

    private void updateFoodCount(Government government) {
    }

    private void updatePopularity(Government government) {
    }
}
