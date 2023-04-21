package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class AttackingBuildingType extends BuildingType {
    private final int boostInFireRange;
    private final int boostInDefenseRange;

    static {
        // TODO: add attacking building types
    }

    AttackingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int boostInFireRange, int boostInDefenseRange) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.boostInFireRange = boostInFireRange;
        this.boostInDefenseRange = boostInDefenseRange;
    }

    public int getBoostInFireRange() {
        return boostInFireRange;
    }

    public int getBoostInDefenseRange() {
        return boostInDefenseRange;
    }
}