package model;

import model.government.Government;
import model.environment.Map;

public class Battle {
    private final Government[] governments;
    private final Map battleMap;
    private int currentPlayerIndex;

    public Battle(int mapSize, Government... governments) {
        this.governments = governments;
        battleMap = new Map(mapSize);
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

    public void goToNextPlayer() {
    }
}
