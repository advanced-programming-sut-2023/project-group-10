package org.example.controller;

import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.view.enums.messages.CustomizeMapMessages;

public class CustomizeMapController {
    private static Map map;

    public static Map getMap() {
        return map;
    }

    public static void newMap(int size) {
        map = new Map(size);
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate position) {
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if (map.getBlockByRowAndColumn(position.row, position.column).getBuilding() != null)
            return CustomizeMapMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        map.getBlockByRowAndColumn(position.row, position.column).setTexture(BlockTexture.getTypeByName(landType));
        return CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate point1, Coordinate point2) {
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        // I assumed point 1 is at the left of point 2
        for (int i = point1.row; i <= point2.row; i++) {
            for (int j = point1.column; j <= point2.column; j++) {
                if (map.getBlockByRowAndColumn(i, j).getBuilding() != null)
                    return CustomizeMapMessages.BUILDING_IN_THE_AREA;
            }
        }
        for (int i = point1.row; i <= point2.row; i++) {
            for (int j = point1.column; j <= point2.column; j++) {
                map.getBlockByRowAndColumn(i, j).setTexture(BlockTexture.getTypeByName(landType));
            }
        }
        return CustomizeMapMessages.SET_TEXTURE_OF_AREA_SUCCESSFUL;
    }


    public static CustomizeMapMessages clear(Coordinate position) {
        if (!map.isIndexInBounds(position.row) || !map.isIndexInBounds(position.column))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        map.getBlockByRowAndColumn(position.row, position.column).clearForces();
        return CustomizeMapMessages.SUCCESSFUL_CLEAR;
    }

    public static CustomizeMapMessages dropRock(Coordinate position, String direction) {
        if (true) return CustomizeMapMessages.NON_EMPTY_LAND;

        return CustomizeMapMessages.DROP_ROCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages dropTree(Coordinate position, String type) {
        return null;
    }
}
