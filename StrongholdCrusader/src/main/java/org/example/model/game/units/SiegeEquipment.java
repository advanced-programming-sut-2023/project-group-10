package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;

import java.util.ArrayList;

public class SiegeEquipment extends MilitaryUnit {
    private final ArrayList<Engineer> engineers;

    public SiegeEquipment(String role, Government government) {
        super(role, government);
        this.engineers = new ArrayList<>();
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public void addEngineers(Engineer... engineers) {
        for (int i = 0; this.engineers.size() < ((MilitaryEquipmentRole) getRole()).getNumberOfEngineersNeeded(); i++)
            this.engineers.add(engineers[i]);
    }
}