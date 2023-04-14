package main.java.view.enums.commands;

public enum UnitMenuCommands {
    MOVE_UNIT(""),
    PATROL_UNIT(""),
    SET_STATE(""),
    ATTACK(""),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD(""),
    DISBAND("");


    public final String regex;

    UnitMenuCommands(String regex) {
        this.regex=regex;

    }
}
