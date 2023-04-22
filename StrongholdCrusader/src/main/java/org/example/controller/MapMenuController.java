package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Tree;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.utils.ASCIIColor;

public class MapMenuController {
    private static final int blocksInARow = 50;
    private static final int blocksInAColumn = 50;

    public static String showMap(int x, int y) {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        map.setOrigin(new Coordinate(x, y), blocksInARow, blocksInAColumn);
        return generateMapView(map);
    }

    public static String moveMap(String directions) {
        //TODO: extract horizontal and vertical change from directions String
        int horizontalChange = 0;
        int verticalChange = 0;
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

    public static String showDetails(int x, int y) {
        return null;
    }
}
