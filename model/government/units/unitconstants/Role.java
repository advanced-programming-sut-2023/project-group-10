package model.government.units.unitconstants;

import java.util.ArrayList;

public class Role {
    private static final ArrayList<Role> allRoles = new ArrayList<>();
    private final String name;
    private final int maxHitPoint;
    private final Quality speed;

    static {
        new Role("peasant", 50, Quality.AVERAGE);
        new Role("lady", 400, Quality.LOW);
        new Role("jester", 50, Quality.AVERAGE);
        new Role("juggler", 50, Quality.AVERAGE);
        new Role("fire eater", 50, Quality.AVERAGE);
        new Role("child", 50, Quality.AVERAGE);
        new Role("mother and babies", 50, Quality.AVERAGE);
        new Role("drunkard", 50, Quality.LOW);
    }

    Role(String name, int maxHitPoint, Quality speed) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.speed = speed;
        allRoles.add(this);
    }

    public static Role getRoleByName(String name) {
        name = name.replaceAll("[\\s_-]", "").toLowerCase();
        for (Role role : allRoles)
            if (role.name.replaceAll("\\s", "").equals(name)) return role;
        return null;
    }

    public String getName() {
        return name;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public Quality getSpeed() {
        return speed;
    }
}