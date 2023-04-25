package org.example.controller;

import org.example.model.game.envirnmont.Coordinate;
import org.example.view.enums.messages.CustomizeMapMessages;

public class CustomizeMapController {
    public static CustomizeMapMessages setTexture(String landType, Coordinate position) {
        if (false) return CustomizeMapMessages.BUILDING_EXISTS_IN_THE_BLOCK;
        return CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL;
    }

    public static CustomizeMapMessages setTexture(String landType, Coordinate point1, Coordinate point2) {
        if (false) return CustomizeMapMessages.BUILDING_IN_THE_AREA;
        return CustomizeMapMessages.SET_TEXTURE_OF_AREA_SUCCESSFUL;
    }


    public static CustomizeMapMessages clear(Coordinate position) {
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
