package model.government.people;

import model.environment.Coordinate;
import model.government.Government;
import model.government.Item;

import java.util.ArrayList;

public class MilitaryUnit extends Person {
    private Quality attackRating;
    private Quality attackRange;
    private Quality defenseRating;
    private Item weapon;
    private Item[] armors;
    private int cost;
    private boolean canClimbLadders;
    private boolean canDigMoats;
    private UnitStance stance;
    private final ArrayList<Coordinate> path;

    public MilitaryUnit(String role, Government government) {
        super(role, government);
        Role job = Role.getRoleByName(role);
        switch (job) {
            case ARCHER:
                setUnitSpecificConstants(Quality.LOW, Quality.AVERAGE, Quality.LOW, Item.BOW, 12, true, true);
                //TODO: add other units
        }
        // default stance
        stance = UnitStance.STAND_GROUND;
        path = new ArrayList<>();
    }

    private void setUnitSpecificConstants(Quality attackRating, Quality attackRange, Quality defenseRating, Item weapon, int cost, boolean canClimbLadders, boolean canDigMoats, Item... armors) {
        this.attackRating = attackRating;
        this.attackRange = attackRange;
        this.defenseRating = defenseRating;
        this.weapon = weapon;
        this.cost = cost;
        this.canClimbLadders = canClimbLadders;
        this.canDigMoats = canDigMoats;
        this.armors = armors;
    }

    public Quality getAttackRating() {
        return attackRating;
    }

    public Quality getAttackRange() {
        return attackRange;
    }

    public Quality getDefenseRating() {
        return defenseRating;
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item[] getArmors() {
        return armors;
    }

    public int getCost() {
        return cost;
    }

    public boolean isCanClimbLadders() {
        return canClimbLadders;
    }

    public boolean isCanDigMoats() {
        return canDigMoats;
    }

    public UnitStance getStance() {
        return stance;
    }


    public void attackEnemy(Person enemy) {
    }

    public void patrol(Coordinate startingPoint, Coordinate endPoint) {
    }

    public void attackHere(Coordinate target) {
    }

    public void disband() {
    }

    public void digMoat(Coordinate target) {
    }

    public void changeStance(UnitStance newStance) {
    }

    public Coordinate getNextPointOnPath() {
        return path.get(0);
    }
}