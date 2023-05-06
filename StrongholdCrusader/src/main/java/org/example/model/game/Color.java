package org.example.model.game;

public enum Color {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    ORANGE("orange"),
    PURPLE("purple"),
    BLACK("black"),
    WHITE("white");
    private String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Color getColorByName(String name){
        for(Color color : Color.values()){
            if(color.getName().equals(name))
                return color;
        }
        return null;
    }
}
