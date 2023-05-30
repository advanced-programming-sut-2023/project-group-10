package org.example.model.game.envirnmont;

import org.example.model.utils.ASCIIColor;

import java.util.LinkedHashMap;

public enum BlockTexture {
    //on land
    EARTH("earth", false, true, true, true, ASCIIColor.RED_BACKGROUND, "earth.png"),
    EARTH_AND_STONES("earth and stones", false, true, true, true, ASCIIColor.RESET, "earth-and-stones.png"),
    BOULDERS("boulders", false, true, true, true, ASCIIColor.YELLOW_BACKGROUND, "boulders.png"),
    ROCKS("rocks", false, false, false, false, ASCIIColor.YELLOW_BACKGROUND, "rocks.png"),
    IRON("iron", false, true, true, false, ASCIIColor.RED_BACKGROUND, "iron.png"),
    GRASS("grass", true, true, true, true, ASCIIColor.GREEN_BACKGROUND, "grass.png"),
    SCRUB("scrub", false, true, true, true, ASCIIColor.GREEN_BACKGROUND, "scrub.png"),
    THICK_SCRUB("thick scrub", false, true, true, true, ASCIIColor.GREEN_BACKGROUND, "thick-scrub.png"),
    //on water
    OIL("oil", false, false, true, false, ASCIIColor.BLACK_BACKGROUND, "oil.png"),
    MARSH("marsh", false, false, true, false, ASCIIColor.PURPLE_BACKGROUND, "marsh.png"), /* units can spawn or walk here but drown if they do */
    FORD("ford", false, false, true, false, ASCIIColor.CYAN_BACKGROUND, "ford.png"),
    RIVER("river", false, false, false, false, ASCIIColor.BLUE_BACKGROUND, "river.png"),
    SMALL_POND("small pond", false, false, false, false, ASCIIColor.CYAN_BACKGROUND, "small-pond.png"),
    LARGE_POND("large pond", false, false, false, false, ASCIIColor.CYAN_BACKGROUND, "large-pond.png"),
    BEACH("beach", false, true, true, true, ASCIIColor.WHITE_BACKGROUND, "beach.png"),
    SEA("sea", false, false, false, false, ASCIIColor.BLUE_BACKGROUND, "sea.png");

    private static final String textureListAssetsFolderPath = BlockTexture.class.getResource("/images/textures/list").toExternalForm();
    private final String name;
    private final boolean fertile;
    private final boolean buildable;
    private final boolean walkable;
    private final boolean isPlantable;
    private final ASCIIColor color;
    private final String listAssetFileName;

    BlockTexture(String name, boolean fertile, boolean buildable, boolean walkable, boolean plantable, ASCIIColor color, String listAssetFileName) {
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

    public ASCIIColor getColor() {
        return color;
    }

    public String getListAssetFileName() {
        return listAssetFileName;
    }
}