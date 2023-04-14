package view.enums.commands;

public enum MapMenuCommands {
    SHOW_MAP(""),
    MOVE_MAP(""),
    SHOW_DETAILS("");
    public final String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }
}
