package model.government.people;

public enum Role {
    //available for employment
    PEASANT(false),
    //can't be employed
    LORD(false),
    LADY(false),
    JESTER(false),
    JUGGLER(false),
    FIRE_EATER(false),
    CHILD(false),
    MOTHER_AND_BABIES(false),
    DRUNKARD(false),
    WOODCUTTER(false),
    HUNTER(false),
    FARMER(false),
    STONE_MASON(false),
    IRON_MINER(false),
    PITCH_DIGGER(false),
    MILL_BOY(false),
    BAKER(false),
    BREWER(false),
    INNKEEPER(false),
    FLETCHER(false),
    ARMORER(false),
    POLETURNER(false),
    BLACKSMITH(false),
    TANNER(false),
    PRIEST(false),
    MARKET_TRADER(false),
    ARCHER(true),
    CROSSBOWMAN(true),
    SPEARMAN(true),
    PIKEMAN(true),
    MACEMAN(true),
    SWORDSMAN(true),
    KNIGHT(true),
    TUNNELER(true),
    LADDERMAN(true),
    ENGINEER(true),
    BLACK_MONK(true),
    ARABIAN_BOW(true),
    SLAVE(true),
    SLINGER(true),
    ASSASSIN(true),
    HORSE_ARCHER(true),
    ARABIAN_SWORDSMAN(true),
    FIRE_THROWER(true);

    private boolean inMilitary;

    Role(boolean inMilitary) {
        this.inMilitary = inMilitary;
    }

    public static Role getRoleByName(String name) {
        name = name.replaceAll("[\\s_-]", "").toUpperCase();
        for (Role role : Role.values())
            if (role.name().replaceAll("_", "").equals(name)) return role;
        return null;
    }

    public boolean isInMilitary() {
        return inMilitary;
    }
}