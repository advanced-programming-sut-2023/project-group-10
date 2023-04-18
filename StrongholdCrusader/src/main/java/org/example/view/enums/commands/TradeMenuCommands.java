package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE(""),
    TRADE_LIST(""),
    HISTORY(""),
    ACCEPT("");
    private String regex;
    TradeMenuCommands(String  regex){
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
