package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.*;
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
        if (!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if (map.getBlockByRowAndColumn(position).getBuilding() != null)
            return CustomizeMapMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        map.setTextureSingleBlock(BlockTexture.getTypeByName(landType), position.row, position.column);
        return CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate point1, Coordinate point2) {
        // TODO: change here
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if (BlockTexture.getTypeByName(landType).equals(BlockTexture.LARGE_POND)
                || BlockTexture.getTypeByName(landType).equals(BlockTexture.SMALL_POND))
            return CustomizeMapMessages.POND_ENTERED;
        for (int i = point1.row; i <= point2.row; i++) {
            for (int j = point1.column; j <= point2.column; j++) {
                if (map.getBlockByRowAndColumn(i, j).getBuilding() != null)
                    return CustomizeMapMessages.BUILDING_IN_THE_AREA;
            }
        }
        map.setTextureRectangleOfBlocks(BlockTexture.getTypeByName(landType), point1.row, point1.column, point2.row, point2.column);
        return CustomizeMapMessages.SET_TEXTURE_OF_AREA_SUCCESSFUL;
    }


    public static CustomizeMapMessages clear(Coordinate position) {
        if (!map.isIndexInBounds(position))
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
        if (!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if (TreeType.getTreeTypeByName(type) == null)
            return CustomizeMapMessages.INVALID_TREE_TYPE;
        if (!map.getBlockByRowAndColumn(position).getTexture().isPlantable())
            return CustomizeMapMessages.INCOMPATIBLE_LAND;
        map.getBlockByRowAndColumn(position).setDroppable(new Tree(TreeType.getTreeTypeByName(type)));
        return CustomizeMapMessages.SUCCESSFUL_TREE_DROP;
    }

    public static boolean isIndexInBounds(int index) {
        return index>=0 && index< map.getSize();
    }
}
