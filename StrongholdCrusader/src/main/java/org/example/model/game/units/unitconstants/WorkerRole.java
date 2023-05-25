package org.example.model.game.units.unitconstants;

import org.example.model.game.buildings.buildingconstants.BuildingTypeName;

public class WorkerRole extends Role {
    private final static int defaultHitPoint = 50;
    private final BuildingTypeName workplaceTypeName;

    private WorkerRole(RoleName name, int maxHitPoint, Quality speed, BuildingTypeName workplaceTypeName) {
        super(name, maxHitPoint, speed);
        this.workplaceTypeName = workplaceTypeName;
    }

    public static void initializeRoles() {
        new WorkerRole(RoleName.WOODCUTTER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.WOODCUTTER);
        new WorkerRole(RoleName.HUNTER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.HUNTER_POST);
        new WorkerRole(RoleName.APPLE_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.APPLE_ORCHARD);
        new WorkerRole(RoleName.DAIRY_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.DAIRY_FARM);
        new WorkerRole(RoleName.HOPS_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.HOPS_FARM);
        new WorkerRole(RoleName.WHEAT_FARMER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.WHEAT_FARM);
        new WorkerRole(RoleName.STONE_MASON, defaultHitPoint, Quality.LOW, BuildingTypeName.QUARRY);
        new WorkerRole(RoleName.IRON_MINER, defaultHitPoint, Quality.LOW, BuildingTypeName.IRON_MINE);
        new WorkerRole(RoleName.PITCH_DIGGER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.PITCH_RIG);
        new WorkerRole(RoleName.MILL_BOY, defaultHitPoint, Quality.HIGH, BuildingTypeName.MILL);
        new WorkerRole(RoleName.BAKER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.BAKERY);
        new WorkerRole(RoleName.BREWER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.BREWERY);
        new WorkerRole(RoleName.INNKEEPER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.INN);
        new WorkerRole(RoleName.TANNER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.TANNER);
        new WorkerRole(RoleName.FLETCHER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.FLETCHER);
        new WorkerRole(RoleName.ARMORER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.ARMOURER);
        new WorkerRole(RoleName.POLETURNER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.POLETURNER);
        new WorkerRole(RoleName.BLACKSMITH, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.BLACKSMITH);
        new WorkerRole(RoleName.MARKET_TRADER, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.MARKET);
        new WorkerRole(RoleName.PRIEST, defaultHitPoint, Quality.AVERAGE, BuildingTypeName.CATHEDRAL);
    }

    public static RoleName getRoleNameByWorkplace(BuildingTypeName buildingTypeName) {
        for (Role role : getAllRoles())
            if (role instanceof WorkerRole && ((WorkerRole) role).getWorkplaceTypeName() == buildingTypeName)
                return role.getName();
        return null;
    }

    public BuildingTypeName getWorkplaceTypeName() {
        return workplaceTypeName;
    }
}
