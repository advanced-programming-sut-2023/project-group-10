package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class PopularityIncreasingBuildingType extends BuildingType {
    private final int increaseInPopularity;

    static {
        new PopularityIncreasingBuildingType(BuildingTypeName.INN, defaultHitPoint * 3, 100, (HashMap<Item, Integer>) Map.of(Item.WOOD, 20), 1, 1);
        new PopularityIncreasingBuildingType(BuildingTypeName.CHAPEL, defaultHitPoint * 3, 250, new HashMap<>(), 0, 2);
        new PopularityIncreasingBuildingType(BuildingTypeName.CATHEDRAL, defaultHitPoint * 3, 1000, new HashMap<>(), 1, 2);

    }

    public PopularityIncreasingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int employeeCount, int increaseInPopularity) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, employeeCount);
        this.increaseInPopularity = increaseInPopularity;
    }

    public int getIncreaseInPopularity() {
        return increaseInPopularity;
    }
}
