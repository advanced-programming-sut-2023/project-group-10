package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("^\\s*profile\\s+change\\s+-u\\s+(?<username>.*)\\s*$"),
    CHANGE_NICKNAME("^\\s*profile\\s+change\\s+-n\\s+(?<nickname>.*)\\s*$"),
    CHANGE_PASSWORD("^\\s*profile\\s+change\\s+password\\s+.+$"),
    CHANGE_EMAIL("^\\s*profile\\s+change\\s+-e\\s+(?<email>.*)\\s*$"),
    CHANGE_SLOGAN("^\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>.*)\\s*$"),
    REMOVE_SLOGAN("^\\s*profile\\s+remove\\s+slogan\\s*$"),
    DISPLAY_HIGHSCORE("^\\s*profile\\s+display\\s+highscore\\s*$"),
    DISPLAY_RANK("^\\s*profile\\s+display\\s+rank\\s*$"),
    DISPLAY_SLOGAN("^\\s*profile\\s+display\\s+slogan\\s*$"),
    DISPLAY_PROFILE("^\\s*profile\\s+display\\s*$");

    private final String regex;

    ProfileMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
