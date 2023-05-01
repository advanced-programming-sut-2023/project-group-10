package org.example.model.game;

import org.example.model.game.envirnmont.Map;

public class Battle {
    private final Government[] governments;
    private final Map battleMap;
    private int currentPlayerIndex;
    private int turnsPassed;

    public Battle(Map map, Government... governments) {
        this.governments = governments;
        battleMap = map;
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

    public Government getGovernmentByOwnerId(String id) {
        for (Government government : governments)
            if (government.getOwner().getUsername().equals(id)) return government;
        return null;
    }

    public void goToNextPlayer() {
        currentPlayerIndex += 1;
        if (currentPlayerIndex == governments.length) {
            currentPlayerIndex = 0;
            turnsPassed++;
        }
    }
}