package org.example.model.game.buildings;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

public class Stairs extends Building {
    public Stairs(Coordinate position, Government government, String buildingType) {
        super(position, government, BuildingTypeName.STAIRS);
        makeNeighborsClimbable();
    }

    private void makeNeighborsClimbable() {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (map.isIndexInBounds(getPosition().row + i) && map.isIndexInBounds(getPosition().column + j))
                    map.getBlockByRowAndColumn(getPosition().row + i, getPosition().column + j).getBuilding().setClimbable(true);
    }
}
