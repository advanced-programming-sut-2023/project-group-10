package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class Tunneler extends MilitaryUnit {
    private boolean inTunnel;

    public Tunneler(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        inTunnel=false;
    }

    public boolean isInTunnel() {
        return inTunnel;
    }

    public void setInTunnel(boolean inTunnel) {
        this.inTunnel = inTunnel;
    }

    public void digTunnelHere(Coordinate target) {
    }
}
