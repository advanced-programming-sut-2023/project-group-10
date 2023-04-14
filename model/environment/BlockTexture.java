package model.environment;

public enum BlockTexture {
    //on land
    EARTH(false, true, true),
    EARTH_AND_STONES(false, true, true),
    BOULDERS(false, true, true),
    ROCKS(false, false, false),
    IRON(false, true, true),
    GRASS(true, true, true),
    SCRUB(false, true, true),
    THICK_SCRUB(false, true, true),
    //on water
    OIL(false, false, true),
    MARSH(false, false, true), /* units can spawn or walk here but drown if they do */
    FORD(false, false, true),
    RIVER(false, false, false),
    SMALL_POND(false, false, false),
    LARGE_POND(false, false, false),
    BEACH(false, true, true),
    SEA (false, false, false);

    private final boolean fertile;
    private final boolean buildable;
    private final boolean walkable;

    BlockTexture(boolean fertile, boolean buildable, boolean walkable) {
        this.fertile = fertile;
        this.buildable = buildable;
        this.walkable = walkable;
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
}