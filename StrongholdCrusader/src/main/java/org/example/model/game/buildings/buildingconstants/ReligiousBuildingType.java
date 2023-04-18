package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class ReligiousBuildingType extends BuildingType {
    private final int increaseInPopularity;

    public ReligiousBuildingType(String name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int increaseInPopularity) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.increaseInPopularity = increaseInPopularity;
    }

    public int getIncreaseInPopularity() {
        return increaseInPopularity;
    }
}
