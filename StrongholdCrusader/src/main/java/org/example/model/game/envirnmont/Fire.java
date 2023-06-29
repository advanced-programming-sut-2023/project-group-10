package org.example.model.game.envirnmont;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import org.example.model.Stronghold;
import org.example.model.game.buildings.Building;

import java.util.ArrayList;

public class Fire {
    private final static ArrayList<Fire> allFires = new ArrayList<>();
    private final static int FIRE_DAMAGE = 40;
    int turnStarted;
    ImageView fireGraphics;
    Transition fireTransition;
    Coordinate position;

    public Fire(int turnStarted, Coordinate position) {
        this.turnStarted = turnStarted;
        allFires.add(this);
        this.position = position;
    }

    public static void putOutFinishedFires() {
        for (int i = 0; i < allFires.size(); ) {
            if (allFires.get(i).isStillOn(Stronghold.getCurrentBattle().getTurnsPassed())) {
                i++;
                continue;
            }
            allFires.get(i).fireTransition.stop();
            ((Group) allFires.get(i).fireGraphics.getParent()).getChildren().remove(allFires.get(i).fireGraphics);
            allFires.remove(i);
        }
    }

    public static void applyDamageToBuildings() {
        for (Fire fire : allFires) {
            Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(fire.position).getBuilding();
            if (building != null) {
                building.changeHitPoint(-FIRE_DAMAGE);
                if (building.getHitPoint() <= 0) building.deleteBuildingFromMapAndGovernmentAndView();
            }
        }
    }

    public ImageView getFireGraphics() {
        return fireGraphics;
    }

    public void setFireGraphics(ImageView fireGraphics) {
        this.fireGraphics = fireGraphics;
    }

    public Transition getFireTransition() {
        return fireTransition;
    }

    public void setFireTransition(Transition fireTransition) {
        this.fireTransition = fireTransition;
    }

    public int getTurnStarted() {
        return turnStarted;
    }

    public boolean isStillOn(int turnPassed) {
        return (turnPassed - turnStarted < 3);
    }
}
