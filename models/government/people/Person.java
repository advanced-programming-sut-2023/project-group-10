package model.government.people;

import model.government.Government;

public class Person {
    private final String jobTitle;
    private Government government;
    private int hitPoint;
    private final Quality speedRating;

    public Person(String role, Government government) {
        this.jobTitle = role;
        this.government = government;
        hitPoint = 100;
        if (Role.getRoleByName(role) == Role.LORD) hitPoint *= 3;

        switch (Role.getRoleByName(role)) {
            case ARCHER:
                speedRating = Quality.HIGH;
                break;
            default:
                speedRating = Quality.AVERAGE;
                break;
            //TODO: add other jobs
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Government getGovernment() {
        return government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getSpeed() {
        return speedRating.getValue() * 5;
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