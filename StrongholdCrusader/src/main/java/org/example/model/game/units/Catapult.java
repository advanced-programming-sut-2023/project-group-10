package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class Catapult extends SiegeEquipment {
    private int rocksLeft;

    public Catapult(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        this.rocksLeft = 20;
    }

    public int getRocksLeft() {
        return rocksLeft;
    }

    public void changeRockCount(int count) {
        rocksLeft += count;
    }

}