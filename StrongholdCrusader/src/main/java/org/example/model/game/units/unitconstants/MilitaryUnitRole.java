package org.example.model.game.units.unitconstants;

import org.example.model.game.Government;

public abstract class MilitaryUnitRole extends Role {
    private final Quality attackRating;
    private final Quality attackRange;
    private final Quality accuracy;
    private final int cost;

    protected MilitaryUnitRole(RoleName name, int maxHitPoint, Quality speed, Quality attackRating, Quality attackRange, Quality accuracy, int cost) {
        super(name, maxHitPoint, speed);
        this.attackRating = attackRating;
        this.attackRange = attackRange;
        this.accuracy = accuracy;
        this.cost = cost;
    }

    public Quality getAttackRating() {
        return attackRating;
    }

    public Quality getAttackRange() {
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