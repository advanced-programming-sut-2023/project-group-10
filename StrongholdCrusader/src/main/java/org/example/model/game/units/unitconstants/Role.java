package org.example.model.game.units.unitconstants;

import java.util.ArrayList;

public class Role {
    private static final ArrayList<Role> allRoles = new ArrayList<>();
    private final RoleName name;
    private final int maxHitPoint;
    private final Quality speed;

    public static void initializeRoles() {
        new Role(RoleName.PEASANT, 50, Quality.AVERAGE);
        new Role(RoleName.LADY, 400, Quality.LOW);
        new Role(RoleName.JESTER, 50, Quality.AVERAGE);
        new Role(RoleName.JUGGLER, 50, Quality.AVERAGE);
        new Role(RoleName.FIRE_EATER, 50, Quality.AVERAGE);
        new Role(RoleName.CHILD, 50, Quality.AVERAGE);
        new Role(RoleName.MOTHER_AND_BABIES, 50, Quality.AVERAGE);
        new Role(RoleName.DRUNKARD, 50, Quality.LOW);
    }

    protected Role(RoleName name, int maxHitPoint, Quality speed) {
        this.name = name;
        this.maxHitPoint = maxHitPoint;
        this.speed = speed;
        allRoles.add(this);
    }

    public static Role getRoleByName(RoleName name) {
        for (Role role : allRoles)
            if (role.name == name) return role;
        return null;
    }

    public static ArrayList<Role> getAllRoles() {
        return allRoles;
    }

    public RoleName getName() {
        return name;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public Quality getSpeed() {
        return speed;
    }
}