package model;

import model.environment.Map;
import model.government.Government;

public class Battle {
    private final Government[] governments;
    private final Map battleMap;
    private int currentPlayerIndex;
    private int turnsPassed;

    public Battle(int mapSize, Government... governments) {
        this.governments = governments;
        battleMap = new Map(mapSize);
        turnsPassed = currentPlayerIndex = 0;
    }

    public Government[] getGovernments() {
        return governments;
    }

    public Map getBattleMap() {
        return battleMap;
    }

    public Government getGovernmentAboutToPlay() {
        return governments[currentPlayerIndex];
    }

    public int getTurnsPassed() {
        return turnsPassed;
    }

    public void goToNextPlayer() {
    }

    private void moveAllUnits(Government government) {
    }

    private void attackAllUnits(Government government) {
    }

    private void produceItems(Government government) {
    }

    private void collectTaxes(Government government) {
    }

    private void producePeasants (Government government) {
    }

    private void updateFoodCount(Government government) {
    }

    private void updatePopularity(Government government) {
    }
}