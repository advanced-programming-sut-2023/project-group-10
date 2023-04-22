package org.example.model.game.units.unitconstants;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.SiegeEquipment;

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

    public int numberOfUnitsThatCanBeSpawned(Government government) {
        return ((int) government.getGold()) / cost;
    }

    public int tryToProduceThisMany(Government government, Coordinate position, int count) {
        count = Math.min(count, numberOfUnitsThatCanBeSpawned(government));
        government.changeGold(this.getCost() * count);
        for (int i = 0; i < count; i++)
            if (this instanceof MilitaryPersonRole) new MilitaryPerson(position, this.getName(), government);
            else new SiegeEquipment(position, this.getName(), government);
        return count;
    }
}