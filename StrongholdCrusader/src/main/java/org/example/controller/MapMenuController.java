package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Item;
import org.example.model.game.Tree;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.utils.ASCIIColor;

import java.util.ArrayList;

public class MapMenuController {
    private static final int blocksInARow = 50;
    private static final int blocksInAColumn = 50;

    public static String showMap(Coordinate origin) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        map.setOrigin(origin, blocksInARow, blocksInAColumn);
        return generateMapView(map);
    }

    public static String moveMap(int horizontalChange, int verticalChange) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        map.moveOrigin(horizontalChange, verticalChange);
        return generateMapView(map);
    }

    private static String generateMapView(Map map) {
        String result = "";
        char info;
        for (int i = map.getTopLeftBlockCoordinate().row; i < map.getTopLeftBlockCoordinate().row + blocksInAColumn; i++) {
            for (int j = map.getTopLeftBlockCoordinate().column; j < map.getTopLeftBlockCoordinate().column + blocksInARow; j++) {
                if (map.getBlockByRowAndColumn(i, j).getAllMilitaryUnits().size() != 0) info = 'S';
                else if (map.getBlockByRowAndColumn(i, j).getBuilding() != null)
                    info = 'B'; /*TODO: put W or B based on building type*/
                else if (map.getBlockByRowAndColumn(i, j).getDroppable() instanceof Tree) info = 'T';
                else info = '#';
                result += map.getBlockByRowAndColumn(i, j).getTexture().getColor().getCode() + info + ASCIIColor.RESET.getCode();
            }
            result += "\n";
        }
        return result;
    }

    //TODO:
    public static String showDetails(Coordinate position) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!map.isIndexInBounds(position))
            return "You've entered invalid index!";
        String details = "";
        details = details.concat("Texture : " + map.getBlockByRowAndColumn(position).getTexture().name().toLowerCase() + "\n");
        details = details.concat("Building : " + map.getBlockByRowAndColumn(position).getBuilding().getBuildingType().getName() + "\n");
        if (!map.getBlockByRowAndColumn(position).getBuilding().getGovernment().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay()))
            details = details.concat("The owner is user \" " + map.getBlockByRowAndColumn(position).getBuilding().getGovernment().getOwner().getNickname()
                    + " \" with username : " + map.getBlockByRowAndColumn(position).getBuilding().getGovernment().getOwner().getUsername() + "\n");
        else {
            if (map.getBlockByRowAndColumn(position).getBuilding() instanceof ItemProducingBuilding) {
                String resources = "";
                for (java.util.Map.Entry<Item, Integer> itemIntegerEntry : map.getBlockByRowAndColumn(position).
                        getBuilding().getBuildingType().getResourcesNeeded().entrySet()) {
                    if (itemIntegerEntry.getValue() != 0)
                        resources = resources.concat(itemIntegerEntry.getValue() + " of item \" " + itemIntegerEntry.getKey() + " \" is needed!," +
                                "The owner owns " + Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getItemCount(itemIntegerEntry.getKey()) + " of this item!\n");
                }
                if (resources.length() == 0)
                    resources = "This building doesn't require any resources\n";
                details = details.concat(resources);
            }
        }
        ArrayList<MilitaryUnit> militaryUnits = map.getBlockByRowAndColumn(position).getAllMilitaryUnits();
        ArrayList<MilitaryPerson> militaryPeople = new ArrayList<>();
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if (militaryUnit instanceof MilitaryPerson)
                militaryPeople.add((MilitaryPerson) militaryUnit);
        }
        details = details.concat("Military People count : " + militaryPeople.size() + "\n");
        for (int i = 1; i <= militaryPeople.size(); i++) {
            details = details.concat(i + "." + militaryPeople.get(i).getRole() + "\n");
        }
        return details;
    }
}
