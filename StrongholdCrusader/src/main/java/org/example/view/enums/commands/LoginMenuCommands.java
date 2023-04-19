package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    LOGIN("^\\s*user\\s+login\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>.+)\\s*$"),
    FORGET_PASSWORD("^\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>.+)\\s*$"),
    LOGOUT("^\\s*user\\s+logout\\s*$");

    private final String regex;

    LoginMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
