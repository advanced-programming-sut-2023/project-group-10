package model.government.units.unitconstants;

import model.government.units.Quality;

public class WorkerRole extends Role {
    private Building workplace;

    static {
        //TODO: add worker initialization
        //WOODCUTTER, HUNTER, FARMER, STONE_MASON ,IRON_MINER ,PITCH_DIGGER, MILL_BOY, BAKER, BREWER, INNKEEPER, FLETCHER, ARMORER, POLETURNER, BLACKSMITH, TANNER,  PRIEST, MARKET_TRADER
    }

    public WorkerRole(String name, int maxHitPoint, Quality speed, Building workplace) {
        super(name, maxHitPoint, speed);
        this.workplace=workplace;
    }

    public Building getWorkplace() {
        return workplace;
    }
}
