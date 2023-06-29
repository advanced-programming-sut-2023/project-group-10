package org.example;

public enum ServerToClientCommands {
    ;
    private final String command;
    private final String[] attributes;

    ServerToClientCommands(String command, String... attributes) {
        this.command = command;
        this.attributes = attributes;
    }
}
