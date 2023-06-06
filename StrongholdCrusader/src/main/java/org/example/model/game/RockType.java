package org.example.model.game;

import java.util.LinkedHashMap;

public enum RockType {
    EAST("rock (east)", "east.png"),
    NORTH("rock (north)", "north.png"),
    SOUTH("rock (south)", "south.png"),
    WEST("rock (west)", "west.png");

    private static final String rockListAssetsFolderPath = Rock.class.getResource("/images/rocks").toExternalForm();
    private final String name;
    private final String listAssetFileName;

    RockType(String name, String listAssetFileName) {
        this.name = name;
        this.listAssetFileName = listAssetFileName;
    }

    public static String getRockListAssetsFolderPath() {
        return rockListAssetsFolderPath;
    }

    public static LinkedHashMap<String, String> getItemNameFileNameMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (RockType value : values())
            result.put(value.getName(), value.getListAssetFileName());
        //TODO: add different assets for random
        result.put("random", values()[(int) (Math.random() * values().length)].getListAssetFileName());
        return result;
    }

    public static RockType getRockTypeByName(String name) {
        for (RockType rockType : values())
            if (rockType.name.equals(name)) return rockType;
        // TODO: add random
        return null;
    }


    public String getName() {
        return name;
    }

    public String getListAssetFileName() {
        return listAssetFileName;
    }
}
