package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CustomizeMapCommands {
    SET_TEXTURE("\\s*set\\s*texture.*"),
    CLEAR("\\s*clear.*"),
    DROP_ROCK("\\s*drop\\s*rock.*"),
    DROP_TREE("\\s*drop\\s*tree.*"),
    END_CUSTOMIZATION("\\s*end\\s+customization\\s*");
    public final String regex;

    CustomizeMapCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, CustomizeMapCommands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
