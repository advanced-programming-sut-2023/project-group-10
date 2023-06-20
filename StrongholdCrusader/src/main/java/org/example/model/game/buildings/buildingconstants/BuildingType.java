package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildingType {
    protected final static int defaultHitPoint = 100;
    private final static ArrayList<BuildingType> allBuildingTypes = new ArrayList<>();
    private final BuildingTypeName name;
    private final int maxHitPoint;
    private final int buildingCost;
    private final Map<Item, Integer> resourcesNeeded;
    private final int employeeCount;
    private final boolean isRepairable;
    private final BuildingCategory category;

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, int employeeCount, boolean isRepairable, BuildingCategory category) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.buildingCost = buildingCost;
        this.resourcesNeeded = resourcesNeeded;
        this.employeeCount = employeeCount;
        this.isRepairable = isRepairable;
        allBuildingTypes.add(this);
        this.category = category;
    }

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable, BuildingCategory category) {
        this(name, maxHitPoint, buildingCost, resourcesNeeded, 0, isRepairable, category);
    }

    public static void initializeTypes() {
        new BuildingType(BuildingTypeName.SMALL_STONE_GATEHOUSE, defaultHitPoint * 6, 0, null, true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.LARGE_STONE_GATEHOUSE, defaultHitPoint * 6, 0, Map.of(Item.STONE, 20), true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.DRAWBRIDGE, defaultHitPoint * 2, 0, Map.of(Item.WOOD, 10), true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.KILLING_PIT, defaultHitPoint, 0, Map.of(Item.WOOD, 6), true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.MARKET, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 5), 1, false, BuildingCategory.INDUSTRY);
        new BuildingType(BuildingTypeName.OX_TETHER, defaultHitPoint, 0, Map.of(Item.WOOD, 5), 1, false, BuildingCategory.INDUSTRY);
        new BuildingType(BuildingTypeName.HOVEL, defaultHitPoint * 3, 0, Map.of(Item.WOOD, 6), false, BuildingCategory.TOWN);
        new BuildingType(BuildingTypeName.CAGED_WAR_DOGS, defaultHitPoint * 3, 100, Map.of(Item.WOOD, 10), 0, true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.SIEGE_TENT, defaultHitPoint, 0, new HashMap<>(), 1, true, BuildingCategory.CASTLE);
        new BuildingType(BuildingTypeName.WALL, defaultHitPoint / 2, 0, Map.of(Item.STONE, 1), true, BuildingCategory.UNKNOWN);
        new BuildingType(BuildingTypeName.STAIRS, defaultHitPoint / 4, 0, Map.of(Item.STONE, 1), true, BuildingCategory.UNKNOWN);
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

    public boolean isCapturable() {
        return name == BuildingTypeName.SMALL_STONE_GATEHOUSE || name == BuildingTypeName.LARGE_STONE_GATEHOUSE;
    }

    public BuildingCategory getCategory() {
        if(this.category == null)
            return BuildingCategory.UNKNOWN;
        else return category;
    }
}
