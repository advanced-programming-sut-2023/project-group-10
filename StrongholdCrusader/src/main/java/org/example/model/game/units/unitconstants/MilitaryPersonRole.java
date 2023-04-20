package org.example.model.game.units.unitconstants;

import org.example.model.game.Government;
import org.example.model.game.Item;

public class MilitaryPersonRole extends MilitaryUnitRole {
    private static final int defaultHitPoint = 50;
    private final Item weapon;
    private final Item[] armors;
    private final boolean canClimbLadders;
    private final boolean canDigMoats;

    static {
        new MilitaryPersonRole(RoleName.LORD, defaultHitPoint * Quality.EXTREMELY_HIGH.getValue(), Quality.LOW, Quality.EXTREMELY_HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 0, null, false, false);
        new MilitaryPersonRole(RoleName.ARCHER, defaultHitPoint * Quality.LOW.getValue(), Quality.HIGH, Quality.LOW, Quality.AVERAGE, Quality.AVERAGE, 12, Item.BOW, true, true);
        new MilitaryPersonRole(RoleName.CROSSBOWMAN, defaultHitPoint * Quality.AVERAGE.getValue(), Quality.AVERAGE, Quality.LOW, Quality.LOW, Quality.HIGH, 20, Item.CROSSBOW, false, false);
        new MilitaryPersonRole(RoleName.SPEARMAN, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.AVERAGE, Quality.AVERAGE, Quality.ZERO, Quality.EXTREMELY_HIGH, 8, Item.SPEAR, true, true);
        new MilitaryPersonRole(RoleName.PIKEMAN, defaultHitPoint * Quality.HIGH.getValue(), Quality.LOW, Quality.AVERAGE, Quality.ZERO, Quality.EXTREMELY_HIGH, 20, Item.PIKE, false, true, Item.METAL_ARMOR);
        new MilitaryPersonRole(RoleName.MACEMAN, defaultHitPoint * Quality.AVERAGE.getValue(), Quality.AVERAGE, Quality.HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 20, Item.MACE, true, true, Item.LEATHER_ARMOR);
        new MilitaryPersonRole(RoleName.SWORDSMAN, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.EXTREMELY_LOW, Quality.VERY_HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 40, Item.SWORD, false, false, Item.METAL_ARMOR);
        new MilitaryPersonRole(RoleName.KNIGHT, defaultHitPoint * Quality.HIGH.getValue(), Quality.VERY_HIGH, Quality.VERY_HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 40, Item.SWORD, false, true, Item.METAL_ARMOR, Item.HORSE);
        new MilitaryPersonRole(RoleName.TUNNELER, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.HIGH, Quality.AVERAGE, Quality.ZERO, Quality.EXTREMELY_HIGH, 30, null, false, false);
        new MilitaryPersonRole(RoleName.LADDERMAN, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.HIGH, Quality.ZERO, Quality.ZERO, Quality.ZERO, 3, null, false, false);
        new MilitaryPersonRole(RoleName.ENGINEER, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.AVERAGE, Quality.ZERO, Quality.ZERO, Quality.ZERO, 30, null, false, true);
        new MilitaryPersonRole(RoleName.BLACK_MONK, defaultHitPoint * Quality.AVERAGE.getValue(), Quality.LOW, Quality.AVERAGE, Quality.ZERO, Quality.EXTREMELY_HIGH, 10, null, false, false);
        new MilitaryPersonRole(RoleName.ARABIAN_BOW, defaultHitPoint * Quality.LOW.getValue(), Quality.HIGH, Quality.LOW, Quality.AVERAGE, Quality.AVERAGE, 75, null, false, true);
        new MilitaryPersonRole(RoleName.SLAVE, defaultHitPoint * Quality.EXTREMELY_LOW.getValue(), Quality.HIGH, Quality.EXTREMELY_LOW, Quality.ZERO, Quality.EXTREMELY_HIGH, 5, null, false, true);
        new MilitaryPersonRole(RoleName.SLINGER, defaultHitPoint * Quality.VERY_LOW.getValue(), Quality.HIGH, Quality.LOW, Quality.VERY_LOW, Quality.AVERAGE, 12, null, false, false);
        new MilitaryPersonRole(RoleName.ASSASSIN, defaultHitPoint * Quality.AVERAGE.getValue(), Quality.AVERAGE, Quality.AVERAGE, Quality.ZERO, Quality.EXTREMELY_HIGH, 60, null, false, false);
        new MilitaryPersonRole(RoleName.HORSE_ARCHER, defaultHitPoint * Quality.AVERAGE.getValue(), Quality.VERY_HIGH, Quality.HIGH, Quality.AVERAGE, Quality.AVERAGE, 60, null, false, false);
        new MilitaryPersonRole(RoleName.ARABIAN_SWORDSMAN, defaultHitPoint * Quality.HIGH.getValue(), Quality.VERY_HIGH, Quality.HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 80, null, false, false);
        new MilitaryPersonRole(RoleName.FIRE_THROWER, defaultHitPoint * Quality.LOW.getValue(), Quality.VERY_HIGH, Quality.HIGH, Quality.AVERAGE, Quality.AVERAGE, 100, null, false, false);
    }

    private MilitaryPersonRole(RoleName name, int maxHitPoint, Quality speed, Quality attackRating, Quality attackRange, Quality accuracy, int cost, Item weapon, boolean canClimbLadders, boolean canDigMoats, Item... armors) {
        super(name, maxHitPoint, speed, attackRating, attackRange, accuracy, cost);
        this.weapon = weapon;
        this.armors = armors;
        this.canClimbLadders = canClimbLadders;
        this.canDigMoats = canDigMoats;
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item[] getArmors() {
        return armors;
    }

    public boolean isCanClimbLadders() {
        return canClimbLadders;
    }

    public boolean isCanDigMoats() {
        return canDigMoats;
    }

    @Override
    public boolean canBeSpawned(Government government, int count) {
        if (!super.canBeSpawned(government, count)) return false;
        for (Item armor : armors)
            if (government.getItemCount(armor) < count) return false;
        return weapon==null || government.getItemCount(weapon) >= count;
    }
}