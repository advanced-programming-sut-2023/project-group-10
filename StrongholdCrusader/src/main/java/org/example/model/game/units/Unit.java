package org.example.model.game.units;

import org.example.model.Stronghold;
import org.example.model.game.Entity;
import org.example.model.game.Government;
import org.example.model.game.NumericalEnums;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.CommonGFXActions;

public class Unit extends Entity {
    private final Role role;
    private int hitPoint;

    public Unit(Coordinate position, RoleName roleName, Government government) {
        super(position, government);
        role = Role.getRoleByName(roleName);
        hitPoint = role.getMaxHitPoint();
    }

    public static void produceUnits(RoleName roleName, int count, Coordinate position) {
        Government currentGovernment = Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
        for (int i = 0; i < count; i++) {
            if (roleName == RoleName.ENGINEER)
                new Engineer(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else if (roleName == RoleName.TUNNELER)
                new Tunneler(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else if (roleName == RoleName.LADDERMAN)
                new Ladderman(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else if (Role.getRoleByName(roleName) instanceof MilitaryPersonRole)
                new MilitaryPerson(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else if (roleName == RoleName.SIEGE_TOWER)
                new SiegeTower(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else if (Role.getRoleByName(roleName) instanceof MilitaryEquipmentRole)
                new SiegeEquipment(position, roleName, currentGovernment).addToGovernmentAndBlockAndView();
            else
                new Unit(position, roleName, Stronghold.getCurrentBattle().getGovernmentAboutToPlay()).addToGovernmentAndBlockAndView();
        }
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
        if (change < 0 && isNearShield()) change /= NumericalEnums.SHIELD_PROTECTION_COEFFICIENT.getValue();
        hitPoint += change;
    }

    private boolean isNearShield() {
        if (getRole().getName() == RoleName.PORTABLE_SHIELD) return false;
        for (Unit unit : getGovernment().getUnits())
            if (unit.getRole().getName() == RoleName.PORTABLE_SHIELD && unit.getPosition().getDistanceFrom(getPosition()) < NumericalEnums.SHIELD_RANGE.getValue())
                return true;
        return false;
    }

    public boolean isDead() {
        return hitPoint <= 0;
    }

    public void killMe() {
        if (this instanceof MilitaryUnit) {
            MilitaryUnit deadUnit = (MilitaryUnit) this;
            CommonGFXActions.getDeathAnimation(deadUnit.getRole(), deadUnit.getBodyGraphics()).play();
        }
        if (this.getRole().getName() == RoleName.LORD) this.changeHitPoint(-this.getHitPoint());
        else this.getGovernment().deleteUnit(this);
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).removeUnit(this);
    }

    public void addToGovernmentAndBlockAndView() {
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).addUnit(this);
        this.getGovernment().addUnit(this);
        if (this instanceof MilitaryUnit)
            Stronghold.getMapGroupGFX().getChildren().add(((MilitaryUnit) this).refreshBodyGraphics());
    }

    public boolean isAttackable() {
        return true;
    }

    @Override
    public void setPosition(Coordinate newCoordinate) {
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(getPosition()).removeUnit(this);
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(newCoordinate).addUnit(this);
        super.setPosition(newCoordinate);
    }
}