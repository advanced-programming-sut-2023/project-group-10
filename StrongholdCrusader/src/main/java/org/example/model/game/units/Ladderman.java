package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.buildings.Building;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class Ladderman extends MilitaryUnit {
    Government initialGovernment;
    Building capturedBuilding;

    public Ladderman(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
    }

    @Override
    public void updateDestination() {
        Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getEndPoint()).getBuilding();
        if (building != null && building.getBuildingType().isCapturable()) {
            initialGovernment = building.getGovernment();
            capturedBuilding = building;
            capturedBuilding.setGovernment(getGovernment());
        }
        super.updateDestination();
    }

    @Override
    public void killMe() {
        capturedBuilding.setGovernment(initialGovernment);
        super.killMe();
    }
}