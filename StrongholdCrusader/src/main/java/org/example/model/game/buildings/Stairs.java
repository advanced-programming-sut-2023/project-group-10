package org.example.model.game.buildings;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

public class Stairs extends Building {
    public Stairs(Coordinate position, Government government) {
        super(position, government, BuildingTypeName.STAIRS);
        makeNeighborsClimbable();
    }

    public void makeNeighborsClimbable() {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        int[] rowMove = {-1, 0, 0, 1};
        int[] columnMove = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                if (map.isIndexInBounds(getPosition().row + rowMove[i]) && map.isIndexInBounds(getPosition().column + columnMove[j])) {
                    Building building = map.getBlockByRowAndColumn(getPosition().row + rowMove[i],
                            getPosition().column + columnMove[j]).getBuilding();
                    if(building!=null && building.getGovernment().equals(Stronghold.getCurrentBattle().getGovernmentAboutToPlay()))
                        building.setClimbable(true);
                }
            }
    }
}
