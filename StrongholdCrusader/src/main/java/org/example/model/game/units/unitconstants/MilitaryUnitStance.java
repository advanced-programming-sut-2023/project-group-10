package org.example.model.game.units.unitconstants;

public enum MilitaryUnitStance {
    STAND_GROUND("standing"),
    DEFENSIVE_STANCE("defensive"),
    AGGRESSIVE_STANCE("offensive");

    private final String name;

    MilitaryUnitStance(String name) {
        this.name = name;
    }

    public static MilitaryUnitStance getStanceByName(String name) {
        for (MilitaryUnitStance stance : MilitaryUnitStance.values())
            if (stance.name.equals(name)) return stance;
        return null;
    }

    public String getName() {
        return name;
    }
}
