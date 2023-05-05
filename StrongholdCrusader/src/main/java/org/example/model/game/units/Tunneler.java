package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.buildings.Building;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.model.game.units.unitconstants.TunnelerState;

public class Tunneler extends MilitaryUnit {
    private TunnelerState tunnelerState;
    private Building targetBuilding;

    public Tunneler(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        tunnelerState = TunnelerState.NONE;
        targetBuilding = null;
    }

    public TunnelerState getTunnelerState() {
        return tunnelerState;
    }

    public void setTunnelerState(TunnelerState tunnelerState) {
        this.tunnelerState = tunnelerState;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    @Override
    public boolean isAttackable() {
        return tunnelerState != TunnelerState.IN_TUNNEL;
    }

    @Override
    public void updateDestination() {
        super.updateDestination();
        if (tunnelerState == TunnelerState.GOING_TO_DIG_TUNNEL) {
            tunnelerState = TunnelerState.IN_TUNNEL;
            this.moveUnit(targetBuilding.getPosition());
        } else if (tunnelerState == TunnelerState.IN_TUNNEL) {
            targetBuilding.deleteBuildingFromMapAndGovernment();
            this.deleteUnitFromGovernmentAndMap();
        }
    }

    @Override
    public boolean isSelectable() {
        return super.isSelectable() && tunnelerState == TunnelerState.NONE;
    }
}