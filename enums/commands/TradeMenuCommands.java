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
}
