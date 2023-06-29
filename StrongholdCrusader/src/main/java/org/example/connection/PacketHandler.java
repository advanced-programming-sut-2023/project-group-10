package org.example.connection;

import org.example.controller.MainMenuController;
import org.example.controller.ProfileMenuController;
import org.example.view.enums.messages.ProfileMenuMessages;
import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;
import java.util.HashMap;

public class PacketHandler {
    private final Connection connection;

    public PacketHandler(Connection connection) {
        this.connection = connection;
    }

    public void handle(Packet receivedPacket) throws IOException {
        ClientToServerCommands receivedPacketCommand = ClientToServerCommands.getCommandByString(receivedPacket.getCommand());
        switch (receivedPacketCommand) {
            case SIGN_UP:
                handleSignUp(receivedPacket);
                break;
            case LOGOUT:
                handleLogout(receivedPacket);
                break;
            case CHANGE_USERNAME:
                handleChangeUsername(receivedPacket);
                break;
            case CHANGE_PASSWORD:
                handleChangePassword(receivedPacket);
                break;
            case CHANGE_NICKNAME:
                handleChangeNickname(receivedPacket);
                break;
            case CHANGE_EMAIL:
                handleChangeEmail(receivedPacket);
                break;


        }
    }

    private void handleSignUp(Packet receivedPacket) throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        SignupMenuMessages result = null;
        /*= SignupMenuController.createUser()*/
        ;
        Packet toBeSent;
        if (result == SignupMenuMessages.SUCCESS)
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_SIGNUP.getCommand(), null);
        else {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("message", result.name());
            toBeSent = new Packet(ServerToClientCommands.FAILED_SIGNUP.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);
    }

    private void handleLogout(Packet receivedPacket) throws IOException {
        String result = MainMenuController.logout().name();
        Packet toBeSent = new Packet(ServerToClientCommands.LOGGED_OUT.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    private void goToAnotherMenu(String menu, Connection connection) throws IOException {
        Packet toBeSent = new Packet(menu, null);
        connection.sendPacket(toBeSent);
    }

    private void handleChangeUsername(Packet receivedPacket) throws IOException {
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

    private void handleChangePassword(Packet receivedPacket) throws IOException {
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

    private void handleChangeNickname(Packet receivedPacket) throws IOException {
        ProfileMenuController.changeNickname(receivedPacket.getAttribute().get("new nickname"));
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    private void handleChangeEmail(Packet receivedPacket) throws IOException {
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


}
