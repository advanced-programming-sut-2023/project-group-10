package org.example.model.game.envirnmont;

public class Fire {
    int turnStarted;

    public Fire(int turnStarted) {
        this.turnStarted = turnStarted;
    }

    public int getTurnStarted() {
        return turnStarted;
    }

    public void setTurnStarted(int turnStarted) {
        this.turnStarted = turnStarted;
    }

    public boolean isStillOn(int turnPassed) {
        return (turnPassed - turnStarted < 3);
    }
}
