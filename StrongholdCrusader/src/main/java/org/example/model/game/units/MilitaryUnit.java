package org.example.model.game.units;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Moat;
import org.example.model.game.NumericalEnums;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.buildingconstants.AttackingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.envirnmont.Map;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.MilitaryUnitStance;
import org.example.model.game.units.unitconstants.Quality;
import org.example.model.game.units.unitconstants.RoleName;

public abstract class MilitaryUnit extends Unit {
    private MilitaryUnitStance stance;
    private Coordinate startingPoint;
    private Coordinate endPoint;
    private DestinationIndicator destination;
    private boolean onPatrol;
    private Moat moatAboutToBeDug;
    private Moat moatAboutToBeFilled;
    private final Rectangle bodyGraphics;

    public MilitaryUnit(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        stance = MilitaryUnitStance.STAND_GROUND;
        destination = DestinationIndicator.NONE;
        bodyGraphics = new Rectangle();
    }

    public Rectangle refreshBodyGraphics() {
        // TODO: call this method when unit's state has changed
        double WIDTH = ExtendedBlock.getWidth();
        double HEIGHT = ExtendedBlock.getHeight();
        double x0 = ExtendedBlock.getX0();
        bodyGraphics.setFill(null);
        // TODO: change assets based on state
        // idle animation
        bodyGraphics.setFill(new ImagePattern(((MilitaryUnitRole) getRole()).getRoleListImage()));
        // positioning and size
        ImagePattern paint = (ImagePattern) bodyGraphics.getFill();
        double heightToWidthRatio = paint.getImage().getHeight() / paint.getImage().getWidth();
        bodyGraphics.setWidth(WIDTH / 4);
        bodyGraphics.setHeight(heightToWidthRatio * WIDTH / 4);
        Pair<Double, Double> centerPosition = ExtendedBlock.getCenterOfBlockForUnits(getPosition().row, getPosition().column, bodyGraphics.getWidth(), bodyGraphics.getHeight());
        bodyGraphics.relocate(centerPosition.getKey(), centerPosition.getValue());
        bodyGraphics.setMouseTransparent(true);
        bodyGraphics.setPickOnBounds(false);
        return bodyGraphics;
    }

    // TODO: remove later
    public Rectangle getBodyGraphics() {
        return bodyGraphics;
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

    public void setOnPatrol(boolean onPatrol) {
        this.onPatrol = onPatrol;
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

    public int getRange() {
        return (((MilitaryUnitRole) getRole()).getAttackRange().getValue() + getBoostInFireRange()) * NumericalEnums.RANGE_COEFFICIENT.getValue() + 1;
    }

    public int getBoostInFireRange() {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        Building building = map.getBlockByRowAndColumn(getPosition()).getBuilding();
        if (building == null || !(building.getBuildingType() instanceof AttackingBuildingType) || ((MilitaryUnitRole) this.getRole()).getAttackRange() == Quality.ZERO)
            return 0;
        return ((AttackingBuildingType) building.getBuildingType()).getBoostInFireRange();
    }
}