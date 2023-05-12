package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class SiegeTower extends SiegeEquipment {
    Government initialGovernment;

    public SiegeTower(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
    }

    public void captureEnemyBuilding() {
        initialGovernment = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getEndPoint()).getBuilding().getGovernment();
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().addBuilding
                (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getEndPoint()).getBuilding());
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getEndPoint()).getBuilding().getGovernment().
                deleteBuilding(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getEndPoint()).getBuilding());

    }

    @Override
    public void updateDestination() {
        captureEnemyBuilding();
        super.updateDestination();
    }

}
