package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class PopularityIncreasingBuildingType extends BuildingType {
    private final int increaseInPopularity;

    public PopularityIncreasingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, int employeeCount, boolean isRepairable, int increaseInPopularity, BuildingCategory category) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, employeeCount, isRepairable, category);
        this.increaseInPopularity = increaseInPopularity;
    }

    public static void initializeTypes() {
        new PopularityIncreasingBuildingType(BuildingTypeName.INN, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 20), 1, false, 1, BuildingCategory.FOOD_PROCESSING);
        new PopularityIncreasingBuildingType(BuildingTypeName.CHAPEL, defaultHitPoint * 3, 250, new HashMap<>(), 0, false, 2, BuildingCategory.TOWN);
        new PopularityIncreasingBuildingType(BuildingTypeName.CATHEDRAL, defaultHitPoint * 3, 1000, new HashMap<>(), 1, false, 2, BuildingCategory.TOWN);
        new PopularityIncreasingBuildingType(BuildingTypeName.GOOD_THINGS, defaultHitPoint * 3, 20, new HashMap<>(), 0, false, 2, BuildingCategory.TOWN);
        new PopularityIncreasingBuildingType(BuildingTypeName.BAD_THINGS, defaultHitPoint * 3, 40, new HashMap<>(), 0, false, -2, BuildingCategory.TOWN);

    }

    public int getIncreaseInPopularity() {
        return increaseInPopularity;
    }
}
