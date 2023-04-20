package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class StorageBuildingType extends BuildingType{
    private final int capacity;

    static {
    }

    public StorageBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int capacity) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
