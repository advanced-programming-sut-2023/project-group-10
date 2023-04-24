package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    MOVE_UNIT("\\s*move\\s+unit\\s+to.*"),
    PATROL_UNIT("\\s*patrol\\s+unit.*"),
    SET_STATE("\\s*set.*"),
    ATTACK("\\s*attack.*"),
    POUR_OIL("\\s*pour\\s+oil.*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel.*"),
    BUILD("\\s*build.*"),
    DISBAND("\\s*disband\\s+unit\\s*"),
    BACK("\\s*back\\s*");

    private final String regex;

    UnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, UnitMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
