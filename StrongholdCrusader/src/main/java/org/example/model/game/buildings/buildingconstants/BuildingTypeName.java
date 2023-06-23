package org.example.model.game.buildings.buildingconstants;

public enum BuildingTypeName {
    SMALL_STONE_GATEHOUSE,
    LARGE_STONE_GATEHOUSE,
    DRAWBRIDGE,
    LOOKOUT_TOWER,
    PERIMETER_TOWER,
    DEFENCE_TURRET,
    SQUARE_TOWER,
    ROUND_TOWER,
    ARMOURY,
    BARRACKS,
    MERCENARY_POST,
    ENGINEER_GUILD,
    STAIRS,
    WALL,
    KILLING_PIT,
    OIL_SMELTER,
    CAGED_WAR_DOGS,
    SIEGE_TENT,
    STABLE,
    TUNNELER_GUILD,
    APPLE_ORCHARD,
    DAIRY_FARM,
    HOPS_FARM,
    WHEAT_FARM,
    HUNTER_POST,
    BAKERY,
    BREWERY,
    GRANARY,
    INN,
    MILL,
    IRON_MINE,
    MARKET,
    OX_TETHER,
    PITCH_RIG,
    QUARRY,
    STOCKPILE,
    WOODCUTTER,
    HOVEL,
    CHAPEL,
    CATHEDRAL,
    GOOD_THINGS,
    BAD_THINGS,
    ARMOURER,
    BLACKSMITH,
    FLETCHER,
    TANNER,
    POLETURNER;

    public static BuildingTypeName getBuildingTypeNameByNameString(String name) {
        name = name.replaceAll("[\\s_-]", "");
        for (BuildingTypeName buildingTypeName : BuildingTypeName.values())
            if (buildingTypeName.toString().replaceAll("_", "").equalsIgnoreCase(name)) return buildingTypeName;
        return null;
    }
}
