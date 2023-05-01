package org.example.model.game.units;

import org.example.model.game.Entity;
import org.example.model.game.Government;
import org.example.model.game.NumericalEnums;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;

public class Unit extends Entity {
    private final Role role;
    private int hitPoint;
    public Unit(Coordinate position, RoleName roleName, Government government) {
        super(position, government);
        this.role = Role.getRoleByName(roleName);
        hitPoint = this.role.getMaxHitPoint();
        government.addUnit(this);
    }

    public Role getRole() {
        return role;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getSpeed() {
        return role.getSpeed().getValue() * NumericalEnums.SPEED_COEFFICIENT.getValue();
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void reduceHitPoint(int damage) {
        hitPoint -= damage;
    }

    public boolean isDead() {
        return hitPoint <= 0;
    }
}