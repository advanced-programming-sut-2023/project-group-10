package model.government.units.unitconstants;

import model.government.units.Quality;

public class MilitaryEquipmentRole extends MilitaryUnitRole {
    private final int numberOfEngineersNeeded;
    private final int buildTime;

    static {
        //TODO: add siege equipment
        //PORTABLE_SHIELD, BATTERING_RAM, SIEGE_TOWER, CATAPULT, TREBUCHET, FIRE_BALLISTA
    }

    public MilitaryEquipmentRole(String name, int maxHitPoint, Quality speed, int attackRating, int attackRange, Quality accuracy, int cost, int numberOfEngineersNeeded, int buildTime) {
        super(name, maxHitPoint, speed, attackRating, attackRange, accuracy, cost);
        this.numberOfEngineersNeeded = numberOfEngineersNeeded;
        this.buildTime = buildTime;
    }

    public int getNumberOfEngineersNeeded() {
        return numberOfEngineersNeeded;
    }

    public int getBuildTime() {
        return buildTime;
    }
}
