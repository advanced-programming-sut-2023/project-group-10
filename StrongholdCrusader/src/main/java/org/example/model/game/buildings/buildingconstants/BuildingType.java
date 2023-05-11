package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildingType {
    private final static ArrayList<BuildingType> allBuildingTypes = new ArrayList<>();
    protected final static int defaultHitPoint = 100;
    private final BuildingTypeName name;
    private final int maxHitPoint;
    private final int buildingCost;
    private final Map<Item, Integer> resourcesNeeded;
    private final int employeeCount;
    private final boolean isRepairable;

    public static void initializeTypes() {
        //TODO: initialize building types
        new BuildingType(BuildingTypeName.SMALL_STONE_GATEHOUSE, defaultHitPoint * 6, 0, null, true);
        new BuildingType(BuildingTypeName.LARGE_STONE_GATEHOUSE, defaultHitPoint * 6, 0, Map.of(Item.STONE, 20), true);
        new BuildingType(BuildingTypeName.DRAWBRIDGE, defaultHitPoint * 2, 0, Map.of(Item.WOOD, 10), true);
        new BuildingType(BuildingTypeName.KILLING_PIT, defaultHitPoint, 0, Map.of(Item.WOOD, 6), true);
        new BuildingType(BuildingTypeName.MARKET, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 5), 1, false);
        new BuildingType(BuildingTypeName.OX_TETHER, defaultHitPoint, 0, Map.of(Item.WOOD, 5), 1, false);
        new BuildingType(BuildingTypeName.HOVEL, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 6), 0, false);
        new BuildingType(BuildingTypeName.CAGED_WAR_DOGS, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 10), 0, true);
        new BuildingType(BuildingTypeName.SIEGE_TENT, defaultHitPoint, 0, new HashMap<>(), 1, true);
    }

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, int employeeCount, boolean isRepairable) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.buildingCost = buildingCost;
        this.resourcesNeeded = resourcesNeeded;
        this.employeeCount = employeeCount;
        this.isRepairable = isRepairable;
        allBuildingTypes.add(this);
    }

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable) {
        this(name, maxHitPoint, buildingCost, resourcesNeeded, 0, isRepairable);
    }

    public static BuildingType getBuildingTypeByName(BuildingTypeName name) {
        for (BuildingType buildingType : allBuildingTypes)
            if (buildingType.name == name) return buildingType;
        return null;
    }

    public static ArrayList<BuildingType> getAllBuildingTypes() {
        return allBuildingTypes;
    }

    public BuildingTypeName getName() {
        return name;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public Map<Item, Integer> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public boolean isRepairable() {
        return isRepairable;
    }
}
