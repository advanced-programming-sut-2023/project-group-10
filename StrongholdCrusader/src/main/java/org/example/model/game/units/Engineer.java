package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;

public class Engineer extends MilitaryUnit {
    private boolean onBoilingDuty;
    private boolean hasOil;

    public Engineer(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        onBoilingDuty = false;
        hasOil = false;
    }

    public boolean isOnBoilingDuty() {
        return onBoilingDuty;
    }

    public void assignToBoilingOilDuty() {
        onBoilingDuty = true;
    }

    public boolean hasOil() {
        return hasOil;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public SiegeEquipment buildSiegeEquipment(RoleName siegeEquipmentName) {
        if (!(Role.getRoleByName(siegeEquipmentName) instanceof MilitaryUnitRole)) return null;
        MilitaryUnitRole siegeEquipmentType = (MilitaryUnitRole) Role.getRoleByName(siegeEquipmentName);
        if (siegeEquipmentType.numberOfUnitsThatCanBeSpawned(getGovernment()) == 0) return null;
        //TODO: check
        return new SiegeEquipment(getPosition(), siegeEquipmentName, getGovernment());
    }

    @Override
    public void updateDestination() {
        if (this.onBoilingDuty &&
                Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn
                        (getEndPoint()).getBuilding().getBuildingType().getName() == BuildingTypeName.OIL_SMELTER)
            this.setHasOil(true);
        super.updateDestination();
    }
}