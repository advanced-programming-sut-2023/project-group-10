package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.RoleName;

import java.util.ArrayList;

public abstract class MilitaryUnit extends Unit {
    private MilitaryUnitStance stance;
    private final ArrayList<Coordinate> path;
    private Coordinate destination;

    public MilitaryUnit(Coordinate position, RoleName role, Government government) {
        //TODO: check if required resources are available
        super(position, role, government);
        stance = MilitaryUnitStance.STAND_GROUND;
        path = new ArrayList<>();
        this.destination = null;
    }

    public void changeDestination(Coordinate destination) {
        this.destination = destination;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public MilitaryUnitStance getStance() {
        return stance;
    }

    public ArrayList<Coordinate> getPath() {
        return path;
    }

    public void changeStance(MilitaryUnitStance newStance) {
        stance = newStance;
    }

    public void attackEnemy(Unit enemy) {
    }

    public void attackHere(Coordinate target) {
    }

    public void moveUnit(Coordinate startingPoint, Coordinate endPoint) {
    }

    public void patrol(Coordinate startingPoint, Coordinate endPoint) {
    }

    public Coordinate getNextPointOnPath() {
        return path.get(0);
    }

    public void removeFirstElementOfPath() {
        path.remove(0);
    }
}