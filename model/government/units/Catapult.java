package model.government.units;

import model.government.Government;

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