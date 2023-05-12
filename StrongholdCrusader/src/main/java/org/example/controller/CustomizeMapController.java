package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.MapDirections;
import org.example.model.game.Rock;
import org.example.model.game.Tree;
import org.example.model.game.TreeType;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.view.enums.messages.CustomizeMapMessages;

public class CustomizeMapController {
    private static Map map;

    public static void initializeMap() {
        map = Stronghold.getCurrentBattle().getBattleMap();
    }

    public static Map getMap() {
        return map;
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate position) {
        if (!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if (BlockTexture.getTypeByName(landType) == null)
            return CustomizeMapMessages.INVALID_LAND_TYPE;
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.DROPPABLE_IN_THE_BLOCK;
        if (map.getBlockByRowAndColumn(position).isKeep() && !BlockTexture.getTypeByName(landType).isWalkable())
            return CustomizeMapMessages.IS_KEEP;
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
                if (map.getBlockByRowAndColumn(i, j).getDroppable() != null)
                    return CustomizeMapMessages.DROPPABlE_IN_THE_AREA;
                if (map.getBlockByRowAndColumn(i, j).isKeep() && !BlockTexture.getTypeByName(landType).isWalkable())
                    return CustomizeMapMessages.IS_KEEP;
            }
        }
        arrangePoints(point1, point2);
        map.setTextureRectangleOfBlocks(BlockTexture.getTypeByName(landType), point1.row, point1.column, point2.row, point2.column);
        return CustomizeMapMessages.SET_TEXTURE_OF_AREA_SUCCESSFUL;
    }

    private static void arrangePoints(Coordinate p1, Coordinate p2) {
        if (p2.row < p1.row) {
            int tmp = p1.row;
            p1.row = p2.row;
            p2.row = tmp;
        }
        if (p2.column < p1.column) {
            int tmp = p1.column;
            p1.column = p2.column;
            p2.column = tmp;
        }
    }

    public static CustomizeMapMessages clear(Coordinate position) {
        if (!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        map.getBlockByRowAndColumn(position).setDroppable(null);
        map.getBlockByRowAndColumn(position).setTexture(BlockTexture.EARTH);
        return CustomizeMapMessages.SUCCESSFUL_CLEAR;
    }

    public static CustomizeMapMessages dropRock(Coordinate position, String direction) {
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.NON_EMPTY_LAND;
        if (map.getBlockByRowAndColumn(position).isKeep())
            return CustomizeMapMessages.IS_KEEP;
        map.getBlockByRowAndColumn(position).setDroppable(new Rock(MapDirections.getByName(direction)));
        return CustomizeMapMessages.DROP_ROCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages dropTree(Coordinate position, String type) {
        if (!map.isIndexInBounds(position))
            return CustomizeMapMessages.INDEX_OUT_OF_BOUNDS;
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.NON_EMPTY_LAND;
        if (TreeType.getTreeTypeByName(type) == null)
            return CustomizeMapMessages.INVALID_TREE_TYPE;
        if (!map.getBlockByRowAndColumn(position).getTexture().isPlantable())
            return CustomizeMapMessages.INCOMPATIBLE_LAND;
        if (map.getBlockByRowAndColumn(position).isKeep())
            return CustomizeMapMessages.IS_KEEP;
        map.getBlockByRowAndColumn(position).setDroppable(new Tree(TreeType.getTreeTypeByName(type)));
        return CustomizeMapMessages.SUCCESSFUL_TREE_DROP;
    }
}
