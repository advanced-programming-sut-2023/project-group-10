package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    SHOW_FOOD_RATE(""),
    SET_FOOD_RATE(""),
    SET_TAX_RATE(""),
    SHOW_TAX_RATE(""),
    SET_FEAR_RATE(""),
    SHOW_FEAR_RATE("");

    public final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
