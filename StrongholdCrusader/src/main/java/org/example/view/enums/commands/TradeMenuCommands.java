package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {

    TRADE_LIST("^\\s*trade\\s*history\\s*"),
    HISTORY("^\\s*trade\\s*list\\s*"),
    ACCEPT("^\\s*trade\\s*accept\\s*.+"),
    SEND_REQUEST("^\\s*trade\\s*\\-\\s*.+"),
    BACK("^\\s*trade\\s*back\\s*");
    private final String regex;
    TradeMenuCommands(String  regex){
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
