package org.example.model.game;

import java.util.LinkedHashMap;

public class Rock implements Droppable {
    private static final String rockListAssetsFolderPath = Rock.class.getResource("/images/rocks").toExternalForm();
    private final MapDirections direction;

    public Rock(MapDirections direction) {
        this.direction = direction;
    }

    public static String getRockListAssetsFolderPath() {
        return rockListAssetsFolderPath;
    }

    public static LinkedHashMap<String, String> getItemNameFileNameMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("rock (east)", "east.png");
        result.put("rock (north)", "north.png");
        result.put("rock (south)", "south.png");
        result.put("rock (west)", "west.png");
        return result;
    }

    public MapDirections getDirection() {
        return direction;
    }
}