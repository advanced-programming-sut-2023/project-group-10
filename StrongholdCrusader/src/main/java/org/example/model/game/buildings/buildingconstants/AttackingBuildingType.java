package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class AttackingBuildingType extends BuildingType {
    private final int fireRange;
    private final int defendRange;

    static {
        // TODO: add attacking building types
    }

    AttackingBuildingType(String name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int fireRange, int defendRange) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }
}