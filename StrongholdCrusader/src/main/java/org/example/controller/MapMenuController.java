package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Tree;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;
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
        if (map.getBlockByRowAndColumn(position).getBuilding() != null) {
            details = details.concat("Building :" + map.getBlockByRowAndColumn(position).getBuilding().getBuildingType().toString() + " with hitpoints : " +
                    map.getBlockByRowAndColumn(position).getBuilding().getHitPoint() + "\n");
        }
        int count = 0;
        ArrayList<MilitaryUnit> militaryUnits = map.getBlockByRowAndColumn(position).getAllMilitaryUnits();
        HashMap<String, ArrayList<MilitaryPerson>> militaryPeople = new HashMap<>();
        for (MilitaryUnit militaryUnit : militaryUnits) {
            if ((militaryUnit instanceof MilitaryPerson)) {
                ArrayList<MilitaryPerson> newUnits = new ArrayList<>();
                if (militaryPeople.containsKey(militaryUnit.getRole().toString())) {
                    militaryPeople.get(militaryUnit.getRole().getName().toString());
                    newUnits.add((MilitaryPerson) militaryUnit);
                    militaryPeople.put(militaryUnit.getRole().getName().toString(), newUnits);
                } else {
                    newUnits.add((MilitaryPerson) militaryUnit);
                    militaryPeople.put(militaryUnit.getRole().getName().toString(), newUnits);
                }

                count++;
            }
        }
        details = details.concat("Military People count : " + count + "\n");
        for (java.util.Map.Entry<String, ArrayList<MilitaryPerson>> militaryPersonIntegerEntry : militaryPeople.entrySet()) {
            details = details.concat(militaryPersonIntegerEntry.getValue() + " of "
                    + militaryPersonIntegerEntry.getKey() + " with hitpoints:\n");
            for (int i = 0; i < militaryPersonIntegerEntry.getValue().size(); i++) {
                details = details.concat(Integer.toString(militaryPersonIntegerEntry.getValue().get(i).getHitPoint()) + " - ");
            }
            details = details.substring(0, details.length() - 2);
            details = details + "\n";
        }
        return details;

    }

    public static String showDetailsExtended(Coordinate position) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        String details = showDetails(position);
        if (details.equals("You've entered invalid index!"))
            return details;
        ArrayList<Unit> units = map.getBlockByRowAndColumn(position).getAllUnits();
        ArrayList<Unit> peasants = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getRole().equals(Role.getRoleByName(RoleName.PEASANT)))
                peasants.add(unit);
        }
        details=details.concat("you have "+peasants.size()+" peasants\n");
        if(peasants.size()==0)
            return details;
        details=details.concat("Their hitpoints are: \n");
        for (Unit peasant : peasants) {
           details= details.concat(Integer.toString(peasant.getHitPoint())+" - ");
        }
        details=details.substring(0,details.length()-2);
        details=details.concat("\n");
        return details;
    }

}
