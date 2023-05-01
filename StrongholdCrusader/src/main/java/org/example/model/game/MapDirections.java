package org.example.model.game;

import java.util.Random;

public enum MapDirections {
    NORTH("n", 0),
    SOUTH("s", 1),
    EAST("e", 2),
    WEST("w", 3);


    public final String name;
    public final int number;

    MapDirections(String name, int number) {
        this.name = name;
        this.number = number;
    }
    public static MapDirections getRandom(){
        Random ran = new Random();
        return MapDirections.getByNumber(ran.nextInt(4));
    }

    public static MapDirections getByNumber(int number) {
        for (MapDirections value : MapDirections.values()) {
         if(value.number==number)
             return value;
        }
        return null;
    }
    public static MapDirections getByName(String name){
        for (MapDirections value : MapDirections.values()) {
            if (value.name.equals(name))
                return value;
            else if( name.equals("r"))
                return MapDirections.getRandom();

        }
        return null;
    }

}
