package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class StorageBuildingType extends BuildingType {
    private final int capacity;
    private final Item[] storedItemTypes;

    public StorageBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable, int capacity, BuildingCategory category, Item... itemTypes) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, isRepairable, category);
        this.capacity = capacity;
        this.storedItemTypes = itemTypes;
    }

    public static void initializeTypes() {
        new StorageBuildingType(BuildingTypeName.ARMOURY, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 5), true, 50, BuildingCategory.CASTLE, Item.getWeaponsAndArmors());
        new StorageBuildingType(BuildingTypeName.STOCKPILE, defaultHitPoint * 3, 0, new HashMap<>(), false, 50, BuildingCategory.INDUSTRY, Item.getPrimaryItems());
        new StorageBuildingType(BuildingTypeName.GRANARY, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 5), false, 50, BuildingCategory.FOOD_PROCESSING, Item.getFoods());
    }

    public int getCapacity() {
        return capacity;
    }

    public Item[] getStoredItemTypes() {
        return storedItemTypes;
    }
}