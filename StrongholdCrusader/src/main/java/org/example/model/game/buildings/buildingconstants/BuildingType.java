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
    private final HashMap<Item, Integer> resourcesNeeded;
    private final int employeeCount;

    static {
        //TODO: initialize building types
        new BuildingType(BuildingTypeName.SMALL_STONE_GATEHOUSE, defaultHitPoint * 6, 0, null);
        new BuildingType(BuildingTypeName.LARGE_STONE_GATEHOUSE, defaultHitPoint * 6, 0, (HashMap<Item, Integer>) Map.of(Item.STONE, 20));
        new BuildingType(BuildingTypeName.DRAWBRIDGE, defaultHitPoint*2, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 10));
        new BuildingType(BuildingTypeName.KILLING_PIT, defaultHitPoint, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 6));
        new BuildingType(BuildingTypeName.MARKET, defaultHitPoint*3, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 5),1);
        new BuildingType(BuildingTypeName.OX_TETHER, defaultHitPoint, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 5),1);
        new BuildingType(BuildingTypeName.HOVEL, defaultHitPoint*3, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 6),0);
        new BuildingType(BuildingTypeName.CAGED_WAR_DOGS, defaultHitPoint*3, 100, (HashMap<Item, Integer>) Map.of(Item.WOOD, 10),0);
        new BuildingType(BuildingTypeName.SIEGE_TENT, defaultHitPoint, 0, new HashMap<>(), 1);
    }

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, int employeeCount) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.buildingCost = buildingCost;
        this.resourcesNeeded = resourcesNeeded;
        this.employeeCount = employeeCount;
    }

    protected BuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded) {
        this(name, maxHitPoint, buildingCost, resourcesNeeded, 0);
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

    public HashMap<Item, Integer> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }
}
