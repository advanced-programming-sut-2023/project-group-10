package org.example.model.game.units.unitconstants;

import org.example.model.game.Government;
import org.example.model.game.Item;

public class MilitaryPersonRole extends MilitaryUnitRole {
    private final Item weapon;
    private final Item[] armors;
    private final boolean canClimbLadders;
    private final boolean canDigMoats;

    static {
        //TODO: add troop initialization
        //LORD, ARCHER, CROSSBOWMAN, SPEARMAN, PIKEMAN, MACEMAN, SWORDSMAN, KNIGHT, TUNNELER, LADDERMAN, ENGINEER, BLACK_MONK, ARABIAN_BOW, SLAVE, SLINGER, ASSASSIN, HORSE_ARCHER, ARABIAN_SWORDSMAN, FIRE_THROWER
    }

    MilitaryPersonRole(String name, int maxHitPoint, Quality speed, Quality attackRating, Quality attackRange, Quality accuracy, int cost, Item weapon, Item[] armors, boolean canClimbLadders, boolean canDigMoats) {
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
        if(!super.canBeSpawned(government, count)) return false;
        for (Item armor : armors)
            if(government.getItemCount(armor)<count) return false;
        return government.getItemCount(weapon) >= count;
    }
}