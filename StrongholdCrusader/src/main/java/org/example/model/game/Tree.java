package org.example.model.game;

import org.example.model.Stronghold;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

public class Tree implements Droppable {
    private final TreeType type;
    private int woodStorage;

    public Tree(TreeType type) {
        this.type = type;
        this.woodStorage = TreeType.getMaxWoodStorage(this.type);
    }

    public TreeType getType() {
        return type;
    }

    public int getWoodStorage() {
        return woodStorage;
    }


    public void reduceWoodStorage(int amount) {
        this.woodStorage -= TreeType.getMaxWoodStorage(this.type) * amount* NumericalEnums.WOOD_REDUCTION_RATE.getValue();

    }
    public int getAdjacentWoodCutterBuildings(Coordinate position){
        Map map =Stronghold.getCurrentBattle().getBattleMap();
        int count=0;
        for(int i=0; i< map.getSize();i++){
            if(map.getBlockByRowAndColumn(i, position.column).getBuilding().getBuildingType().getName().equals(BuildingTypeName.WOODCUTTER))
                count++;
            if(map.getBlockByRowAndColumn(position.row,i).getBuilding().getBuildingType().getName().equals(BuildingTypeName.WOODCUTTER));
        }

        return count;
    }

}
