package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;

public class ItemProducingBuildingType extends BuildingType {
    private final int rate;
    private final int itemCountProducedPerProduction;
    private final boolean isFarm;
    private final Item item;
    private final HashMap<Item, Integer> resourcesNeededPerItem;

    static {
        // TODO: add attacking building types
    }

    public ItemProducingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int rate, int itemCountProducedPerProduction, boolean isFarm, Item item) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.rate = rate;
        this.itemCountProducedPerProduction = itemCountProducedPerProduction;
        this.isFarm = isFarm;
        this.item = item;
        resourcesNeededPerItem = new HashMap<>();
        if (item.getWoodRequired() != 0) resourcesNeededPerItem.put(Item.WOOD, item.getWoodRequired());
        if (item.getStoneRequired() != 0) resourcesNeededPerItem.put(Item.STONE, item.getStoneRequired());
        if (item.getIronRequired() != 0) resourcesNeededPerItem.put(Item.IRON, item.getIronRequired());
    }

    public int getRate() {
        return rate;
    }

    public int getItemCountProducedPerProduction() {
        return itemCountProducedPerProduction;
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
