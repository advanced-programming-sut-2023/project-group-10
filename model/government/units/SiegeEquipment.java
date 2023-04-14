package model.government.units;

import model.government.Government;

import java.util.ArrayList;

public class SiegeEquipment extends MilitaryUnit{
    private final ArrayList<Engineer> engineers;

    public SiegeEquipment(String role, Government government) {
        super(role, government);
        this.engineers = new ArrayList<>();
    }
}