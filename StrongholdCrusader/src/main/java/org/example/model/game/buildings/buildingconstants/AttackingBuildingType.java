package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;

import java.util.Map;

public class AttackingBuildingType extends BuildingType {
    private final int boostInFireRange;
    private final int boostInDefense;

    private AttackingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable, int boostInFireRange, int boostInDefense, BuildingCategory category) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, isRepairable, category);
        this.boostInFireRange = boostInFireRange;
        this.boostInDefense = boostInDefense;
    }

    public static void initializeTypes() {
        new AttackingBuildingType(BuildingTypeName.LOOKOUT_TOWER, defaultHitPoint * 3, 0, Map.of(Item.STONE, 10), true, 2, 2, BuildingCategory.CASTLE);
        new AttackingBuildingType(BuildingTypeName.PERIMETER_TOWER, defaultHitPoint * 4, 0, Map.of(Item.STONE, 10), true, 2, 2, BuildingCategory.CASTLE);
        new AttackingBuildingType(BuildingTypeName.DEFENCE_TURRET, defaultHitPoint * 5, 0, Map.of(Item.STONE, 15), true, 2, 2, BuildingCategory.CASTLE);
        new AttackingBuildingType(BuildingTypeName.SQUARE_TOWER, defaultHitPoint * 4, 0, Map.of(Item.STONE, 35), true, 2, 3, BuildingCategory.CASTLE);
        new AttackingBuildingType(BuildingTypeName.ROUND_TOWER, defaultHitPoint * 5, 0, Map.of(Item.STONE, 40), true, 2, 3, BuildingCategory.CASTLE);

    }

    public int getBoostInFireRange() {
        return boostInFireRange;
    }

    public int getBoostInDefense() {
        return boostInDefense;
    }
}