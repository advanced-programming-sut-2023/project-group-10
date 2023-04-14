package model.government.units;

import model.government.Government;

public class SiegeTower extends SiegeEquipment{
    public SiegeTower(String role, Government government) {
        super(role, government);
    }

    public void captureEnemyBuilding(){}
}
