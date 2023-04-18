package org.example.model.game.units;

import org.example.model.game.Government;

public class Catapult extends SiegeEquipment {
    private int rocksLeft;

    public Catapult(String role, Government government) {
        super(role, government);
        this.rocksLeft = 20;
    }

    public int getRocksLeft() {
        return rocksLeft;
    }

    public void changeRockCount(int count) {
        rocksLeft += count;
    }
}