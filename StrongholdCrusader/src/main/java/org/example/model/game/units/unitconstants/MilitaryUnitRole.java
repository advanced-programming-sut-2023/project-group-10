package org.example.model.game.units.unitconstants;

import javafx.scene.image.Image;
import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.Unit;

public abstract class MilitaryUnitRole extends Role {
    private final Quality attackRating;
    private final Quality attackRange;
    private final Quality accuracy;
    private final int cost;
    private final Image listImage;

    protected MilitaryUnitRole(RoleName name, int maxHitPoint, Quality speed, Quality attackRating, Quality attackRange, Quality accuracy, int cost) {
        super(name, maxHitPoint, speed);
        this.attackRating = attackRating;
        this.attackRange = attackRange;
        this.accuracy = accuracy;
        this.cost = cost;
        listImage = new Image(RoleName.class.getResource("/images/units/list/" + name.name() + ".png").toExternalForm());
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
        government.changeGold(-this.getCost() * count);
        if (this instanceof MilitaryPersonRole) Unit.produceUnits(getName(), count, position);
        return count;
    }

    public Image getRoleListImage() {
        return listImage;
    }
}