package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;
import org.example.model.game.units.unitconstants.RoleName;

import java.util.ArrayList;

public class SiegeEquipment extends MilitaryUnit {
    private final ArrayList<Engineer> engineers;

    public SiegeEquipment(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        this.engineers = new ArrayList<>();
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public void addEngineers(Engineer... engineers) {
        for (int i = 0; this.engineers.size() < ((MilitaryEquipmentRole) getRole()).getNumberOfEngineersNeeded(); i++)
            this.engineers.add(engineers[i]);
    }

    public int getCurrentNeededEngineers() {
        return ((MilitaryEquipmentRole) this.getRole()).getNumberOfEngineersNeeded() - engineers.size();
    }

    @Override
    public boolean isSelectable() {
        return getCurrentNeededEngineers() == 0;
    }

    @Override
    public void killMe() {
        for (Engineer engineer : engineers) {
            engineer.killMe();
        }
        super.killMe();
    }
}