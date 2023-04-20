package org.example.model.game.units.unitconstants;

import org.example.model.game.buildings.buildingconstants.BuildingTypeName;

public enum RoleName {
    //normal people
    LADY,
    JESTER,
    PEASANT,
    CHILD,
    MOTHER_AND_BABIES,
    DRUNKARD,
    JUGGLER,
    FIRE_EATER,
    //employed people
    WOODCUTTER,
    HUNTER,
    APPLE_FARMER,
    DAIRY_FARMER,
    HOPS_FARMER,
    WHEAT_FARMER,
    STONE_MASON,
    IRON_MINER,
    PITCH_DIGGER,
    MILL_BOY,
    BAKER,
    BREWER,
    INNKEEPER,
    TANNER,
    FLETCHER,
    ARMORER,
    BLACKSMITH,
    POLETURNER,
    PRIEST,
    MARKET_TRADER,
    //military people
    LORD,
    ARCHER,
    CROSSBOWMAN,
    SPEARMAN,
    PIKEMAN,
    MACEMAN,
    SWORDSMAN,
    KNIGHT,
    TUNNELER,
    LADDERMAN,
    ENGINEER,
    BLACK_MONK,
    ARABIAN_BOW,
    SLAVE,
    SLINGER,
    ASSASSIN,
    HORSE_ARCHER,
    ARABIAN_SWORDSMAN,
    FIRE_THROWER,
    //siege equipment
    PORTABLE_SHIELD,
    BATTERING_RAM,
    SIEGE_TOWER,
    CATAPULT,
    TREBUCHET,
    FIRE_BALLISTA;

    public static RoleName getRoleNameByNameString(String name) {
        name = name.replaceAll("[\\s_-]", "");
        for (RoleName roleName : RoleName.values())
            if (roleName.toString().replaceAll("_", "").equalsIgnoreCase(name)) return roleName;
        return null;
    }
}
