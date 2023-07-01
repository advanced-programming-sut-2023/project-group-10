package org.example.connection.Handlers;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.ProfileMenuController;
import org.example.model.User;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.io.IOException;
import java.util.ArrayList;
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
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuMessages result = ProfileMenuController.changeUsername(receivedPacket.getAttribute().get("new username"), currentUser);
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
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuMessages result = ProfileMenuController.changePassword(receivedPacket.getAttribute().get("current password"),
                receivedPacket.getAttribute().get("new password"), currentUser);
        Packet toBeSent;
        HashMap<String, String> attributes = new HashMap<>();
        if (result == ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL) {
            attributes.put("message", "");
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), attributes);
        }
        else {
            attributes.put("message", result.name());
            toBeSent = new Packet(ServerToClientCommands.FAILED_CHANGE.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);
    }

    public void handleChangeNickname() throws IOException {
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuController.changeNickname(receivedPacket.getAttribute().get("new nickname"), currentUser);
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void handleChangeEmail() throws IOException {
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuMessages result = ProfileMenuController.changeEmail(receivedPacket.getAttribute().get("new email"), currentUser);
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
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuController.changeAvatar(receivedPacket.getAttribute().get("new avatar"), currentUser);
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void handleChangeSlogan() throws IOException {
        User currentUser = User.getUserByUsername(connection.getUsername());
        ProfileMenuController.changeSlogan(receivedPacket.getAttribute().get("slogan"), currentUser);
        Packet toBeSent;
        toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_CHANGE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void sortedUsers() throws IOException{
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("array list", arrayListToJson(User.sortUsers()));
        Packet toBeSent = new Packet(ServerToClientCommands.SORTED_USERS.getCommand(),hashMap);
        connection.sendPacket(toBeSent);
    }

    private String arrayListToJson(ArrayList<User> sortedUsers) {
        // null object serialized as empty string
        if (sortedUsers == null) return "";
        return new Gson().toJson(sortedUsers, ArrayList.class);
    }
}
