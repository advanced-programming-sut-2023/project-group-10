package org.example.controller;

import org.checkerframework.checker.units.qual.C;
import org.example.model.game.Rock;
import org.example.model.game.Tree;
import org.example.model.game.TreeType;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.MapDirections;
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
        if(!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if (map.getBlockByRowAndColumn(position).getBuilding() != null)
            return CustomizeMapMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        map.getBlockByRowAndColumn(position).setTexture(BlockTexture.getTypeByName(landType));
        return CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate point1, Coordinate point2) {
        // TODO: change here
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if(  BlockTexture.getTypeByName(landType).equals(BlockTexture.LARGE_POND)
                ||  BlockTexture.getTypeByName(landType).equals(BlockTexture.SMALL_POND))
            return CustomizeMapMessages.POND_ENTERED;
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
        if (!map.isIndexInBounds(position) )
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        map.getBlockByRowAndColumn(position).clearForces();
        return CustomizeMapMessages.SUCCESSFUL_CLEAR;
    }

    public static CustomizeMapMessages dropRock(Coordinate position, String direction) {
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.NON_EMPTY_LAND;
        map.getBlockByRowAndColumn(position).setDroppable(new Rock(MapDirections.getByName(direction)));
        return CustomizeMapMessages.DROP_ROCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages dropTree(Coordinate position, String type) {
        if(!map.isIndexInBounds(position) )
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if(TreeType.getTreeTypeByName(type) == null)
            return CustomizeMapMessages.INVALID_TREE_TYPE;
        if(!map.getBlockByRowAndColumn(position).getTexture().isPlantable())
            return CustomizeMapMessages.INCOMPATIBLE_LAND;
        map.getBlockByRowAndColumn(position).setDroppable(new Tree(TreeType.getTreeTypeByName(type)));
        return CustomizeMapMessages.SUCCESSFUL_TREE_DROP;
    }
}
