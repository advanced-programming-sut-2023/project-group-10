package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.NumericalEnums;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;

public class Unit {
    private Coordinate position;
    private final Role role;
    private final Government government;
    private int hitPoint;

    public Unit(Coordinate position, RoleName roleName, Government government) {
        this.position = position;
        this.role = Role.getRoleByName(roleName);
        this.government = government;
        hitPoint = this.role.getMaxHitPoint();
        government.addUnit(this);
    }

    public Coordinate getPosition() {
        return position;
    }

    public Role getRole() {
        return role;
    }

    public Government getGovernment() {
        return government;
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