package org.example.connection.Handlers;

import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.ProfileMenuController;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.io.IOException;
import java.util.HashMap;

public class ProfileMenuHandler {

    private final Connection connection;
    private Packet receivedPacket;

    public ProfileMenuHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void handleChangeUsername() throws IOException {
        ProfileMenuMessages result = ProfileMenuController.changeUsername(receivedPacket.getAttribute().get("new username"));
        Packet toBeSent;
        if (result == ProfileMenuMessages.CHANGE_USERNAME_SUCCESSFUL)
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        else {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("message", result.name());
            toBeSent = new Packet(ServerToClientCommands.FAILED_CHANGE.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);

    }

    public void handleChangePassword() throws IOException {
        ProfileMenuMessages result = ProfileMenuController.changePassword(receivedPacket.getAttribute().get("old password"),
                receivedPacket.getAttribute().get("new password"));
        Packet toBeSent;
        if (result == ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL)
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        else {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("message", result.name());
            toBeSent = new Packet(ServerToClientCommands.FAILED_CHANGE.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);
    }

    public void handleChangeNickname() throws IOException {
        ProfileMenuController.changeNickname(receivedPacket.getAttribute().get("new nickname"));
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void handleChangeEmail() throws IOException {
        ProfileMenuMessages result = ProfileMenuController.changeEmail(receivedPacket.getAttribute().get("new email"));
        Packet toBeSent;

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message", result.name());
        if (result == ProfileMenuMessages.CHANGE_EMAIL_SUCCESSFUL)
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), attributes);
        else {
            toBeSent = new Packet(ServerToClientCommands.FAILED_CHANGE.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);
    }

    public void handleChangeAvatar() throws IOException {
        ProfileMenuController.changeAvatar(receivedPacket.getAttribute().get("new avatar"));
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }
}
