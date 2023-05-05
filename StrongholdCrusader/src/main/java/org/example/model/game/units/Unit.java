package org.example.model.game.units;

import org.example.model.Stronghold;
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
        role = Role.getRoleByName(roleName);
        hitPoint = role.getMaxHitPoint();
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

    public void changeHitPoint(int change) {
        hitPoint += change;
    }

    public boolean isDead() {
        return hitPoint <= 0;
    }

    public void deleteUnitFromGovernmentAndMap() {
        this.getGovernment().deleteUnit(this);
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).removeUnit(this);
    }

    public boolean addToGovernmentAndBlock() {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).addUnit(this))
            return false;
        this.getGovernment().addUnit(this);
        return true;
    }
}