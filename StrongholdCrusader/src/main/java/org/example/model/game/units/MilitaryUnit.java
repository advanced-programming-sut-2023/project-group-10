package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;

import java.util.ArrayList;

public abstract class MilitaryUnit extends Unit {
    private MilitaryUnitStance stance;
    private final ArrayList<Coordinate> path;

    public MilitaryUnit(String role, Government government) {
        //TODO: check if required resources are available
        super(role, government);
        stance = MilitaryUnitStance.STAND_GROUND;
        path = new ArrayList<>();
    }

    public MilitaryUnitStance getStance() {
        return stance;
    }

    public void changeStance(MilitaryUnitStance newStance) {
    }

    public void attackEnemy(Unit enemy) {
    }

    public void attackHere(Coordinate target) {
    }

    public void moveUnit(Coordinate startingPoint, Coordinate endPoint) {
    }

    public void patrol(Coordinate startingPoint, Coordinate endPoint) {
    }

    public void disband() {
    }

    public Coordinate getNextPointOnPath() {
        return path.get(0);
    }
}