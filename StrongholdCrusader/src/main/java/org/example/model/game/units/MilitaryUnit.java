package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.RoleName;

public abstract class MilitaryUnit extends Unit {
    private MilitaryUnitStance stance;
    private Coordinate startingPoint;
    private Coordinate endPoint;
    private DestinationIndicator destination;
    private boolean onPatrol;

    public MilitaryUnit(Coordinate position, RoleName role, Government government) {
        //TODO: check if required resources are available
        super(position, role, government);
        stance = MilitaryUnitStance.STAND_GROUND;
        destination = DestinationIndicator.NONE;
    }

    public MilitaryUnitStance getStance() {
        return stance;
    }

    public Coordinate getEndPoint() {
        return endPoint;
    }

    public boolean isOnPatrol() {
        return onPatrol;
    }

    public void changeStance(MilitaryUnitStance newStance) {
        stance = newStance;
    }

    public void attackEnemy(Unit enemy) {
    }

    public void attackHere(Coordinate target) {
    }

    public void moveUnit(Coordinate endPoint) {
        destination = DestinationIndicator.END_POINT;
        startingPoint = null;
        this.endPoint = endPoint;
        onPatrol = false;
    }

    public void patrol(Coordinate startingPoint, Coordinate endPoint) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.destination = DestinationIndicator.STARTING_POINT;
        onPatrol = true;
    }

    public Coordinate getDestination() {
        if (destination == DestinationIndicator.STARTING_POINT) return startingPoint;
        if (destination == DestinationIndicator.END_POINT) return endPoint;
        return null;
    }

    public void updateDestination() {
        if (onPatrol) {
            if (destination == DestinationIndicator.STARTING_POINT) destination = DestinationIndicator.END_POINT;
            else if (destination == DestinationIndicator.END_POINT) destination = DestinationIndicator.STARTING_POINT;
        } else {
            destination = DestinationIndicator.NONE;
            startingPoint = endPoint = null;
        }
    }
}