package org.example.model.game.units.unitconstants;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.model.game.envirnmont.BlockTexture;

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

    private static final String unitsListAssetsFolderPath = BlockTexture.class.getResource("/images/units/list").toExternalForm();

    public static RoleName getRoleNameByNameString(String name) {
        name = name.replaceAll("[\\s_-]", "");
        for (RoleName roleName : RoleName.values())
            if (roleName.toString().replaceAll("[\\s_]", "").equalsIgnoreCase(name)) return roleName;
        return null;
    }

    public Image getRoleListImage() {
        return new Image(unitsListAssetsFolderPath + name() + ".png");
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("_", " ").toLowerCase();
    }
}
