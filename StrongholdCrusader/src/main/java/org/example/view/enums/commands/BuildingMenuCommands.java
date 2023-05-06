package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    CREATE_UNIT("^\\s*create\\s+unit\\s*-.+$"),
    SELECT_BUILDING("^\\s*select\\s+building\\s*-.+$"),
    REPAIR("^\\s*repair\\s*$"),
    BACK("^\\s*back\\s*$");


    public final String regex;

    BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, BuildingMenuCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
