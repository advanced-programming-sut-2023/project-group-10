package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Moat;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.buildingconstants.AttackingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.RoleName;

public abstract class MilitaryUnit extends Unit {
    private MilitaryUnitStance stance;
    private Coordinate startingPoint;
    private Coordinate endPoint;
    private DestinationIndicator destination;
    private boolean onPatrol;
    private Moat moatAboutToBeDug;
    private Moat moatAboutToBeFilled;

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

    public void moveUnit(Coordinate endPoint) {
        destination = DestinationIndicator.END_POINT;
        startingPoint = null;
        this.endPoint = endPoint;
        onPatrol = false;
        moatAboutToBeDug = null;
        moatAboutToBeFilled = null;
    }

    public void patrol(Coordinate startingPoint, Coordinate endPoint) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.destination = DestinationIndicator.STARTING_POINT;
        onPatrol = true;
        moatAboutToBeDug = null;
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
            Map map = Stronghold.getCurrentBattle().getBattleMap();
            if (moatAboutToBeDug != null) {
                map.getBlockByRowAndColumn(endPoint).setDroppable(moatAboutToBeDug);
                moatAboutToBeDug = null;
            }
            if (moatAboutToBeFilled != null) {
                map.getBlockByRowAndColumn(moatAboutToBeFilled.getPosition()).setDroppable(null);
                moatAboutToBeFilled = null;
            }
            destination = DestinationIndicator.NONE;
            startingPoint = endPoint = null;
        }
    }

    public void setOnPatrol(boolean onPatrol) {
        this.onPatrol = onPatrol;
    }

    public int getBoostInFireRange() {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        Building building = map.getBlockByRowAndColumn(getPosition()).getBuilding();
        if (building == null || !(building.getBuildingType() instanceof AttackingBuildingType)) return 0;
        return ((AttackingBuildingType) building.getBuildingType()).getBoostInFireRange();
    }

    public void setMoatAboutToBeDug(Moat moatAboutToBeDug) {
        this.moatAboutToBeDug = moatAboutToBeDug;
    }

    public void setMoatAboutToBeFilled(Moat moatAboutToBeFilled) {
        this.moatAboutToBeFilled = moatAboutToBeFilled;
    }

    public boolean isSelectable() {
        return moatAboutToBeDug == null;
    }

    public void stop() {
        startingPoint = null;
        endPoint = null;
        destination = DestinationIndicator.NONE;
        onPatrol = false;
        moatAboutToBeDug = null;
        moatAboutToBeFilled = null;
    }
}