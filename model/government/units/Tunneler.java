package model.government.units;

import model.environment.Coordinate;
import model.government.Government;

public class Tunneler extends MilitaryUnit {
    public Tunneler(String jobTitle, Government government) {
        super(jobTitle, government);
    }

    public void digTunnelHere(Coordinate target) {
    }
}
