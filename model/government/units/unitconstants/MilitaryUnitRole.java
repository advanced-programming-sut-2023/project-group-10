package model.government.units.unitconstants;

import model.government.Government;
import model.government.units.Quality;

public abstract class MilitaryUnitRole extends Role {
    private final int attackRating;
    private final int attackRange;
    private final Quality accuracy;
    private final int cost;

    public MilitaryUnitRole(String name, int maxHitPoint, Quality speed, int attackRating, int attackRange, Quality accuracy, int cost) {
        super(name, maxHitPoint, speed);
        this.attackRating = attackRating;
        this.attackRange = attackRange;
        this.accuracy = accuracy;
        this.cost = cost;
    }

    public int getAttackRating() {
        return attackRating;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public Quality getAccuracy() {
        return accuracy;
    }

    public int getCost() {
        return cost;
    }

    public boolean canBeSpawned(Government government, int count) {
        return government.getGold() >= count * cost;
    }
}