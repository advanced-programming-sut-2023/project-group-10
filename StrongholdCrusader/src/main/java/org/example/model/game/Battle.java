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

    public void removeGovernment(Government government) {
        for (int i = 0; i < governments.length; i++) {
            if (governments[i].equals(government))
                governments[i] = null;
        }
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
        }
        while (true) {
            if (governments[currentPlayerIndex] == null)
                currentPlayerIndex = (currentPlayerIndex + 1) % governments.length;
            else
                break;
        }
        turnsPassed++;
    }

    public int getNumberOfActivePlayers() {
        int count = 0;
        for (Government government : governments) {
            if (government != null)
                count += 1;
        }
        return count;
    }
}