package org.example.model.game;

public enum mapDirections {
    UP("up",0,1),
    DOWN("down",0,-1),
    RIGHT("up",1,0),
    LEFT("up",-1,0);


    public final String name;
    public final int horizontalChange;
    public final int verticalChange;

    mapDirections(String name, int horizontalChange, int verticalChange) {
        this.name = name;
        this.horizontalChange = horizontalChange;
        this.verticalChange = verticalChange;
    }
}
