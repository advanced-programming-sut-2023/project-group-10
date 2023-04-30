package org.example.model.game.envirnmont;

import org.example.model.utils.ASCIIColor;

public enum BlockTexture {
    //on land
    EARTH(false, true, true, ASCIIColor.RESET),
    EARTH_AND_STONES(false, true, true, ASCIIColor.RESET),
    BOULDERS(false, true, true, ASCIIColor.YELLOW_BACKGROUND),
    ROCKS(false, false, false, ASCIIColor.YELLOW_BACKGROUND),
    IRON(false, true, true, ASCIIColor.RED_BACKGROUND),
    GRASS(true, true, true, ASCIIColor.GREEN_BACKGROUND),
    SCRUB(false, true, true, ASCIIColor.GREEN_BACKGROUND),
    THICK_SCRUB(false, true, true, ASCIIColor.GREEN_BACKGROUND),
    //on water
    OIL(false, false, true, ASCIIColor.BLACK_BACKGROUND),
    MARSH(false, false, true, ASCIIColor.PURPLE_BACKGROUND), /* units can spawn or walk here but drown if they do */
    FORD(false, false, true, ASCIIColor.CYAN_BACKGROUND),
    RIVER(false, false, false, ASCIIColor.BLUE_BACKGROUND),
    SMALL_POND(false, false, false, ASCIIColor.CYAN_BACKGROUND),
    LARGE_POND(false, false, false, ASCIIColor.CYAN_BACKGROUND),
    BEACH(false, true, true, ASCIIColor.WHITE_BACKGROUND),
    SEA(false, false, false, ASCIIColor.BLUE_BACKGROUND);

    private final boolean fertile;
    private final boolean buildable;
    private final boolean walkable;
    private final ASCIIColor color;

    BlockTexture(boolean fertile, boolean buildable, boolean walkable, ASCIIColor color) {

        this.fertile = fertile;
        this.buildable = buildable;
        this.walkable = walkable;
        this.color = color;
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

    public ASCIIColor getColor() {
        return color;
    }

    public static BlockTexture getTypeByName(String landType) {
        landType = landType.replaceAll("[\\s_-]", "");
        for (BlockTexture blockTexture : BlockTexture.values())
            if (blockTexture.toString().replaceAll("_", "").equalsIgnoreCase(landType))
                return blockTexture;
        return null;
    }
}