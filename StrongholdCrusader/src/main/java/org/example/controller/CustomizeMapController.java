package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.Rock;
import org.example.model.game.RockType;
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

    public static CustomizeMapMessages setTexture(BlockTexture texture, Coordinate position) {
        if (map.getBlockByRowAndColumn(position).getDroppable() instanceof Tree && !texture.isPlantable())
            return CustomizeMapMessages.INVALID_TEXTURE_FOR_TREE;
        if (map.getBlockByRowAndColumn(position).isKeep() && !texture.isWalkable())
            return CustomizeMapMessages.IS_KEEP;
        map.setTextureSingleBlock(texture, position.row, position.column);
        return CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages clear(Coordinate position) {
        map.getBlockByRowAndColumn(position).setDroppable(null);
        map.getBlockByRowAndColumn(position).setTexture(BlockTexture.EARTH);
        return CustomizeMapMessages.SUCCESSFUL_CLEAR;
    }

    public static CustomizeMapMessages dropRock(Coordinate position, RockType rockType) {
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.NON_EMPTY_LAND;
        if (map.getBlockByRowAndColumn(position).isKeep())
            return CustomizeMapMessages.IS_KEEP;
        map.getBlockByRowAndColumn(position).setDroppable(new Rock(rockType));
        return CustomizeMapMessages.DROP_ROCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages dropTree(Coordinate position, TreeType treeType) {
        if (map.getBlockByRowAndColumn(position).getDroppable() != null)
            return CustomizeMapMessages.NON_EMPTY_LAND;
        if (!map.getBlockByRowAndColumn(position).getTexture().isPlantable())
            return CustomizeMapMessages.INCOMPATIBLE_LAND;
        if (map.getBlockByRowAndColumn(position).isKeep())
            return CustomizeMapMessages.IS_KEEP;
        map.getBlockByRowAndColumn(position).setDroppable(new Tree(treeType));
        return CustomizeMapMessages.SUCCESSFUL_TREE_DROP;
    }
}
