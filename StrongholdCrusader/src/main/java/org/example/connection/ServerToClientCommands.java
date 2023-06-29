package org.example.connection;

public enum ServerToClientCommands {
    FAILED_SIGNUP("failed sign up", "message"),
    SUCCESSFUL_SIGNUP("successful sign up"),
    //main menu responses
    LOGGED_OUT("log out"),
    PROFILE_MENU("profile menu");

    private final String command;
    private final String[] attributes;

    ServerToClientCommands(String command, String... attributes) {
        this.command = command;
        this.attributes = attributes;
    }

    public String getCommand() {
        return command;
    }

    public static ServerToClientCommands getCommandByString(String command) {
        for (ServerToClientCommands value : values())
            if (value.command.equals(command)) return value;
        return null;
    }
}
