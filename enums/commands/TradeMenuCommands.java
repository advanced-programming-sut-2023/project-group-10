package main.java.view.enums.commands;

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
