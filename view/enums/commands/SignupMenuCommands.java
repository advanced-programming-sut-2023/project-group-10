package view.enums.commands;

public enum SignupMenuCommands {
    CREATE_USER(""),
    PICK_QUESTION("");

    public final String regex;

    SignupMenuCommands(String regex) {
        this.regex=regex;
    }
    
    public static Matcher getMatcher(String input, SignupMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }

}
