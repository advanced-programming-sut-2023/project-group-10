package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class StorageBuildingType extends BuildingType {
    private final int capacity;
    private final Item[] storedItemTypes;

    static {
        new StorageBuildingType(BuildingTypeName.ARMOURY, defaultHitPoint * 3, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 5), 50, Item.getWeaponsAndArmors());
        new StorageBuildingType(BuildingTypeName.STOCKPILE, defaultHitPoint * 3, 0, new HashMap<>(), 50, Item.getPrimaryItems());
        new StorageBuildingType(BuildingTypeName.GRANARY, defaultHitPoint * 3, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 5), 50, Item.getFoods());
    }

    public StorageBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int capacity, Item... itemTypes) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.capacity = capacity;
        this.storedItemTypes = itemTypes;
    }

    public int getCapacity() {
        return capacity;
    }

    public Item[] getStoredItemTypes() {
        return storedItemTypes;
    }
}