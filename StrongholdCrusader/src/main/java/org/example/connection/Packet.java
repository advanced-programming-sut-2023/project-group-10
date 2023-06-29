package org.example.connection;


import java.util.HashMap;

public class Packet {
    String command;
    HashMap<String,String> attribute;

    public Packet(String command, HashMap<String, String> attribute) {
        this.command = command;
        this.attribute = attribute;
    }

    public String getCommand() {
        return command;
    }

    public HashMap<String, String> getAttribute() {
        return attribute;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setAttribute(HashMap<String, String> attribute) {
        this.attribute = attribute;
    }
}
