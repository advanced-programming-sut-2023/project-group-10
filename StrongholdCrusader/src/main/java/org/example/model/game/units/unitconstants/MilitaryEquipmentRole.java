package org.example.model.game.units.unitconstants;

public class MilitaryEquipmentRole extends MilitaryUnitRole {
    private static final int defaultHitPoint = 300;
    private final int numberOfEngineersNeeded;
    private final int buildTime;

    static {
        //TODO: add siege equipment
        new MilitaryEquipmentRole(RoleName.PORTABLE_SHIELD, defaultHitPoint, Quality.LOW, Quality.ZERO, Quality.ZERO, Quality.ZERO, 5, 1, 1);
        new MilitaryEquipmentRole(RoleName.BATTERING_RAM, defaultHitPoint, Quality.LOW, Quality.HIGH, Quality.ZERO, Quality.EXTREMELY_HIGH, 150, 4, 2);
        new MilitaryEquipmentRole(RoleName.SIEGE_TOWER, defaultHitPoint, Quality.LOW, Quality.ZERO, Quality.ZERO, Quality.ZERO, 150, 4, 3);
        new MilitaryEquipmentRole(RoleName.CATAPULT, defaultHitPoint, Quality.LOW, Quality.AVERAGE, Quality.AVERAGE, Quality.VERY_HIGH, 150, 2, 2);
        new MilitaryEquipmentRole(RoleName.TREBUCHET, defaultHitPoint, Quality.ZERO, Quality.HIGH, Quality.VERY_HIGH, Quality.AVERAGE, 150, 3, 2);
        new MilitaryEquipmentRole(RoleName.FIRE_BALLISTA, defaultHitPoint, Quality.LOW, Quality.VERY_HIGH, Quality.HIGH, Quality.VERY_HIGH, 150, 2, 2);
    }

    private MilitaryEquipmentRole(RoleName name, int maxHitPoint, Quality speed, Quality attackRating, Quality attackRange, Quality accuracy, int cost, int numberOfEngineersNeeded, int buildTime) {
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
