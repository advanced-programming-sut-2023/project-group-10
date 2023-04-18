package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class ItemProducingBuildingType extends BuildingType {
    private final int rate;
    private final boolean isFarm;
    private final Item item;
    private final HashMap<Item, Integer> resourcesNeededPerItem;

    static {
        // TODO: add attacking building types
    }

    public ItemProducingBuildingType(String name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int rate, boolean isFarm, Item item, HashMap<Item, Integer> resourcesNeededPerItem) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.rate = rate;
        this.isFarm = isFarm;
        this.item = item;
        this.resourcesNeededPerItem = resourcesNeededPerItem;
    }

    public int getRate() {
        return rate;
    }

    public boolean isFarm() {
        return isFarm;
    }

    public Item getItem() {
        return item;
    }

    public HashMap<Item, Integer> getResourcesNeededPerItem() {
        return resourcesNeededPerItem;
    }
}
