package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    SHOW_MAP("\\s*show\\s+map.*"),
    MOVE_MAP("\\s*move\\s+map.*"),
    SHOW_DETAILS("\\s*show\\s+details.*"),
    SHOW_EXTENDED_DETAILS("\\s*show\\s+extended\\s+details.*"),
    BACK("\\s*back\\s*");
    public final String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MapMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
