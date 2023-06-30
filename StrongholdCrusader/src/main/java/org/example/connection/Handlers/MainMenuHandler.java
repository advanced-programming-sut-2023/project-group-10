package org.example.connection.Handlers;

import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.MainMenuController;
import org.example.controller.ProfileMenuController;

import java.io.IOException;

public class MainMenuHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public MainMenuHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public Packet getReceivedPacket() {
        return receivedPacket;
    }

    public void handleLogout() throws IOException {
        MainMenuController.logout();
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.LOGGED_OUT.getCommand(), null);
        connection.sendPacket(toBeSent);
    }


}