package org.example.model.game.envirnmont;

import javafx.scene.paint.Color;

import java.util.LinkedHashMap;

public enum BlockTexture {
    //on land
    EARTH("earth", false, true, true, true, Color.LEMONCHIFFON, "earth.png"),
    EARTH_AND_STONES("earth and stones", false, true, true, true, Color.DARKKHAKI, "earth-and-stones.png"),
    BOULDERS("boulders", false, true, true, true, Color.FIREBRICK, "boulders.png"),
    ROCKS("rocks", false, false, false, false, Color.GRAY, "rocks.png"),
    IRON("iron", false, true, true, false, Color.SILVER, "iron.png"),
    GRASS("grass", true, true, true, true, Color.MEDIUMSPRINGGREEN, "grass.png"),
    SCRUB("scrub", false, true, true, true, Color.LAWNGREEN, "scrub.png"),
    THICK_SCRUB("thick scrub", false, true, true, true, Color.FORESTGREEN, "thick-scrub.png"),
    //on water
    OIL("oil", false, true, true, false, Color.BLACK, "oil.png"),
    MARSH("marsh", false, false, true, false, Color.DARKSEAGREEN, "marsh.png"), /* units can spawn or walk here but drown if they do */
    FORD("ford", false, false, true, false, Color.SADDLEBROWN, "ford.png"),
    RIVER("river", false, false, false, false, Color.DEEPSKYBLUE, "river.png"),
    SMALL_POND("small pond", false, false, false, false, Color.CYAN, "small-pond.png"),
    LARGE_POND("large pond", false, false, false, false, Color.CYAN, "large-pond.png"),
    BEACH("beach", false, true, true, true, Color.SANDYBROWN, "beach.png"),
    SEA("sea", false, false, false, false, Color.DARKBLUE, "sea.png");

    private static final String textureListAssetsFolderPath = BlockTexture.class.getResource("/images/textures/list").toExternalForm();
    private final String name;
    private final boolean fertile;
    private final boolean buildable;
    private final boolean walkable;
    private final boolean isPlantable;
    private final Color color;
    private final String listAssetFileName;

    BlockTexture(String name, boolean fertile, boolean buildable, boolean walkable, boolean plantable, Color color, String listAssetFileName) {
        this.name = name;
        this.isPlantable = plantable;
        this.fertile = fertile;
        this.buildable = buildable;
        this.walkable = walkable;
        this.color = color;
        this.listAssetFileName = listAssetFileName;
    }

    public static String getTextureListAssetsFolderPath() {
        return textureListAssetsFolderPath;
    }

    public static BlockTexture getTypeByName(String landType) {
        landType = landType.replaceAll("[\\s_-]", "");
        for (BlockTexture blockTexture : BlockTexture.values())
            if (blockTexture.toString().replaceAll("_", "").equalsIgnoreCase(landType))
                return blockTexture;
        return null;
    }

    public static LinkedHashMap<String, String> getItemNameFileNameMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (BlockTexture value : values())
            result.put(value.getName(), value.getListAssetFileName());
        return result;
    }

    public String getName() {
        return name;
    }

    public boolean isFertile() {
        return fertile;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isPlantable() {
        return isPlantable;
    }

    public Color getColor() {
        return color;
    }

    public String getListAssetFileName() {
        return listAssetFileName;
    }
}