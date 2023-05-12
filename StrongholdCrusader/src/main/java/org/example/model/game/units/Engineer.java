package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
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

    @Override
    public void updateDestination() {
        if (this.onBoilingDuty &&
                Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn
                        (getEndPoint()).getBuilding().getBuildingType().getName() == BuildingTypeName.OIL_SMELTER)
            this.setHasOil(true);
        super.updateDestination();
    }
}