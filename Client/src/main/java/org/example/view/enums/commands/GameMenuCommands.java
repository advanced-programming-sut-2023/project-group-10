package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SHOW_FOOD_RATE("\\s*food\\s+rate\\s+show\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-.*"),
    SET_TAX_RATE("\\s*tax\\s+rate\\s+-.*"),
    SHOW_TAX_RATE("\\s*tax\\s+rate\\s+show\\s*"),
    SET_FEAR_RATE("\\s*fear\\s+rate\\s+-.*"),
    SHOW_FEAR_RATE("\\s*fear\\s+rate\\s+show\\s*"),
    DROP_BUILDING("\\s*drop\\s*building.*"),
    SELECT_BUILDING("\\s*select\\s+building.*"),
    SELECT_UNIT("\\s*select\\s+unit.*"),
    TRADE_MENU("\\s*trade\\s+menu\\s*"),
    SHOP_MENU("\\s*shop\\s+menu\\s*"),
    MAP_MENU("\\s*map\\s+menu\\s*"),
    ROUNDS_PLAYED("\\s*show\\s+rounds\\s*"),
    SHOW_PLAYER("\\s*show\\s+current\\s*player\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    LEAVE_GAME("\\s*leave\\s+game\\s*"),
    DROP_UNIT("\\s*drop\\s*unit.*"),
    MOUNT_EQUIPMENT("\\s*mount\\s+equipment.*"),
    CLEAR_FORCES("\\s*clear\\s+forces.*"),
    DELETE_STRUCTURE("\\s*delete\\s+structure.*"),
    SHOW_GOLD("\\s*show\\s*gold\\s*");

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
