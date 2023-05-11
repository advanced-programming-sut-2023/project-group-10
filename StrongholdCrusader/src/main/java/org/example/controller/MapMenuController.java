package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Tree;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.utils.ASCIIColor;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (map.getBlockByRowAndColumn(position).getDroppable() instanceof Tree) {
            details = details.concat("Tree of type : " + ((Tree) map.getBlockByRowAndColumn(position).getDroppable())
                    .getType().getName() + "with storage : " + ((Tree) map.getBlockByRowAndColumn(position).getDroppable()).getWoodStorage() + "\n");
        }
        int count = 0;
        ArrayList<MilitaryUnit> militaryUnits = map.getBlockByRowAndColumn(position).getAllMilitaryUnits();
        HashMap<MilitaryPersonRole, Integer> militaryPeople = new HashMap<>();
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if ((militaryUnit instanceof MilitaryPerson)) {
                if (militaryPeople.containsKey(militaryUnit.getRole()))
                    militaryPeople.put((MilitaryPersonRole) militaryUnit.getRole(), militaryPeople.get(militaryUnit.getRole()) + 1);
                else
                    militaryPeople.put((MilitaryPersonRole) militaryUnit.getRole(), 1);

                count++;
            }
        }
        details = details.concat(" Military People count : " + count + "\n");
        for (java.util.Map.Entry<MilitaryPersonRole, Integer> militaryPersonRoleIntegerEntry : militaryPeople.entrySet()) {
            details = details.concat(militaryPersonRoleIntegerEntry.getValue() + " of " + militaryPersonRoleIntegerEntry.getKey() + "\n");
        }
        return details;
    }
}
