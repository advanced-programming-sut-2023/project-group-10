package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {

    CHANGE_USERNAME(""),
    CHANGE_NICKNAME(""),
    CHANGE_PASSWORD(""),
    CHANGE_EMAIL(""),
    CHANGE_SLOGAN(""),
    REMOVE_SLOGAN(""),
    DISPLAY_HIGHSCORE(""),
    DISPLAY_RANK(""),
    DISPLAY_PROFILE("");

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
