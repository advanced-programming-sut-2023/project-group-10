package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemProducingBuildingType extends BuildingType {
    private final int rate;
    private final int itemCountProducedPerProduction;
    private final boolean isFarm;
    private final Item[] items;

    public static void initializeTypes() {
        new ItemProducingBuildingType(BuildingTypeName.MILL, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 20), 3, false, 2, 5, false, Item.FLOUR);
        new ItemProducingBuildingType(BuildingTypeName.IRON_MINE, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 20), 2, false, 2, 3, false, Item.IRON);
        new ItemProducingBuildingType(BuildingTypeName.PITCH_RIG, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 20), 1, false, 2, 2, false, Item.PITCH);
        new ItemProducingBuildingType(BuildingTypeName.QUARRY, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 20), 3, false, 2, 3, false, Item.STONE);
        new ItemProducingBuildingType(BuildingTypeName.WOODCUTTER, defaultHitPoint * 2, 0, Map.of(Item.WOOD, 3), 1, false, 2, 4, false, Item.WOOD);
        new ItemProducingBuildingType(BuildingTypeName.ARMOURER, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 20), 1, false, 1, 2, false, Item.METAL_ARMOR);
        new ItemProducingBuildingType(BuildingTypeName.BLACKSMITH, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 20), 1, false, 1, 1, false, Item.SWORD, Item.MACE);
        new ItemProducingBuildingType(BuildingTypeName.FLETCHER, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 20), 1, false, 1, 1, false, Item.BOW, Item.CROSSBOW);
        new ItemProducingBuildingType(BuildingTypeName.POLETURNER, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 10), 1, false, 1, 2, false, Item.SPEAR, Item.PIKE);
        new ItemProducingBuildingType(BuildingTypeName.BLACKSMITH, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 20), 1, false, 1, 1, false, Item.SWORD, Item.MACE);
        new ItemProducingBuildingType(BuildingTypeName.OIL_SMELTER, defaultHitPoint * 3, 100, Map.of(Item.IRON, 10), 1, false, 1, 1, false);
        new ItemProducingBuildingType(BuildingTypeName.STABLE, defaultHitPoint * 3, 400, Map.of(Item.WOOD, 20), 0, false, 1, 1, false, Item.HORSE);
        new ItemProducingBuildingType(BuildingTypeName.APPLE_ORCHARD, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 5), 1, false, 2, 5, true, Item.APPLE);
        new ItemProducingBuildingType(BuildingTypeName.DAIRY_FARM, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 10), 1, false, 2, 3, true, Item.CHEESE, Item.COW);
        new ItemProducingBuildingType(BuildingTypeName.HOPS_FARM, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 15), 1, false, 2, 5, true, Item.HOPS);
        new ItemProducingBuildingType(BuildingTypeName.WHEAT_FARM, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 15), 1, false, 2, 5, true, Item.WHEAT);
        new ItemProducingBuildingType(BuildingTypeName.HUNTER_POST, defaultHitPoint * 2, 0, Map.of(Item.WOOD, 5), 1, false, 1, 3, false, Item.MEAT);
        new ItemProducingBuildingType(BuildingTypeName.BAKERY, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 10), 1, false, 2, 3, true, Item.BREAD);
        new ItemProducingBuildingType(BuildingTypeName.BREWERY, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 10), 1, false, 1, 2, true, Item.ALE);
    }

    public ItemProducingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, int employeeCount, boolean isRepairable, int rate, int itemCountProducedPerProduction, boolean isFarm, Item... items) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, employeeCount, isRepairable);
        this.rate = rate;
        this.itemCountProducedPerProduction = itemCountProducedPerProduction;
        this.isFarm = isFarm;
        this.items = items;
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

    public Item[] getItems() {
        return items;
    }
}
