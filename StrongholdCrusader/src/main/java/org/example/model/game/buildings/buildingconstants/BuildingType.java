package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingType {
    private final static ArrayList<BuildingType> allBuildingTypes = new ArrayList<>();

    private final String name;
    private final int maxHitPoint;
    private boolean isClimbable;
    private final int buildingCost;
    private final HashMap<Item, Integer> resourcesNeeded;

    static {
        //TODO: initialize building types
    }

    BuildingType(String name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.buildingCost = buildingCost;
        this.resourcesNeeded = resourcesNeeded;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        name = name.replaceAll("[\\s_-]", "").toLowerCase();
        for (BuildingType buildingType : allBuildingTypes)
            if (buildingType.name.replaceAll("\\s", "").equals(name)) return buildingType;
        return null;
    }

    public String getName() {
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

    public boolean isClimbable() {
        return isClimbable;
    }
}