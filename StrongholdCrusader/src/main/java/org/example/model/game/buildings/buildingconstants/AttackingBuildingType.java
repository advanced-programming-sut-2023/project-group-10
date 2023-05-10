package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.HashMap;
import java.util.Map;

public class AttackingBuildingType extends BuildingType {
    private final int boostInFireRange;
    private final int boostInDefense;

    public static void initializeTypes() {
        // TODO: add attacking building types
        new AttackingBuildingType(BuildingTypeName.LOOKOUT_TOWER, defaultHitPoint * 3, 0, Map.of(Item.STONE, 10), true, 2, 2);
        new AttackingBuildingType(BuildingTypeName.PERIMETER_TOWER, defaultHitPoint * 4, 0, Map.of(Item.STONE, 10), true, 2, 2);
        new AttackingBuildingType(BuildingTypeName.DEFENCE_TURRET, defaultHitPoint * 5, 0, Map.of(Item.STONE, 15), true, 2, 2);
        new AttackingBuildingType(BuildingTypeName.SQUARE_TOWER, defaultHitPoint * 4, 0, Map.of(Item.STONE, 35), true, 2, 3);
        new AttackingBuildingType(BuildingTypeName.ROUND_TOWER, defaultHitPoint * 5, 0, Map.of(Item.STONE, 40), true, 2, 3);

    }

    private AttackingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable, int boostInFireRange, int boostInDefense) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, isRepairable);
        this.boostInFireRange = boostInFireRange;
        this.boostInDefense = boostInDefense;
    }

    public int getBoostInFireRange() {
        return boostInFireRange;
    }

    public int getBoostInDefense() {
        return boostInDefense;
    }
}