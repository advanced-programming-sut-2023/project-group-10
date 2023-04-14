package model.government.people;

import model.government.Government;

public class Worker extends Person {
    private final Building workplace;

    public Worker(String role, Government government) {
        super(role, government);
        //TODO: complete switch case after adding buildings
        switch (Role.getRoleByName(role)) {

        }
    }

    public Building getWorkplace() {
        return workplace;
    }
}