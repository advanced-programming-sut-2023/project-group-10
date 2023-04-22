package org.example.model.game.units.unitconstants;

import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;

public class WorkerRole extends Role {
    private final static int defaultHitPoint = 50;
    private final BuildingType workplaceType;

    static {
        new WorkerRole(RoleName.WOODCUTTER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.WOODCUTTER));
        new WorkerRole(RoleName.HUNTER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.HUNTER_POST));
        new WorkerRole(RoleName.APPLE_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.APPLE_ORCHARD));
        new WorkerRole(RoleName.DAIRY_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.DAIRY_FARM));
        new WorkerRole(RoleName.HOPS_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.HOPS_FARM));
        new WorkerRole(RoleName.WHEAT_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.WHEAT_FARM));
        new WorkerRole(RoleName.STONE_MASON, defaultHitPoint, Quality.LOW, BuildingType.getBuildingTypeByName(BuildingTypeName.QUARRY));
        new WorkerRole(RoleName.IRON_MINER, defaultHitPoint, Quality.LOW, BuildingType.getBuildingTypeByName(BuildingTypeName.IRON_MINE));
        new WorkerRole(RoleName.PITCH_DIGGER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.PITCH_RIG));
        new WorkerRole(RoleName.MILL_BOY, defaultHitPoint, Quality.HIGH, BuildingType.getBuildingTypeByName(BuildingTypeName.MILL));
        new WorkerRole(RoleName.BAKER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.BAKERY));
        new WorkerRole(RoleName.BREWER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.BREWERY));
        new WorkerRole(RoleName.INNKEEPER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.INN));
        new WorkerRole(RoleName.TANNER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.TANNER));
        new WorkerRole(RoleName.FLETCHER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.FLETCHER));
        new WorkerRole(RoleName.ARMORER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.ARMOURER));
        new WorkerRole(RoleName.POLETURNER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.POLETURNER));
        new WorkerRole(RoleName.BLACKSMITH, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.BLACKSMITH));
        new WorkerRole(RoleName.MARKET_TRADER, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.MARKET));
        new WorkerRole(RoleName.PRIEST, defaultHitPoint, Quality.AVERAGE, BuildingType.getBuildingTypeByName(BuildingTypeName.CATHEDRAL));
    }

    private WorkerRole(RoleName name, int maxHitPoint, Quality speed, BuildingType workplaceType) {
        super(name, maxHitPoint, speed);
        this.workplaceType = workplaceType;
    }

    public BuildingType getWorkplaceType() {
        return workplaceType;
    }
}
