package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    MOVE_UNIT("\\s*move\\s+unit\\s+to.*"),
    PATROL_UNIT("\\s*patrol\\s+unit.*"),
    SET_STANCE("\\s*set.*"),
    ATTACK("\\s*attack.*"),
    POUR_OIL("\\s*pour\\s+oil.*"),
    ASSIGN_TO_OIL_DUTY("\\s*assign\\s+to\\s+boiling\\s+oil\\s+duty.*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel.*"),
    DIG_MOAT("\\s*dig\\s+moat.*"),
    FILL_MOAT("\\s*fill\\s+moat.*"),
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
