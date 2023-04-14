package main.java.view.enums.commands;

public enum GameMenuCommands {
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    SHOW_FOOD_RATE(""),
    SET_FOOD_RATE(""),
    SET_TAX_RATE(""),
    SHOW_TAX_RATE(""),
    SET_FEAR_RATE(""),
    DROP_BUILDING(""),
    DROP_UNIT("");
    public final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }
}
