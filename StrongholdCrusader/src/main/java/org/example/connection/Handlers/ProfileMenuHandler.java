package org.example.connection.Handlers;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.ConnectionDatabase;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.ProfileMenuController;
import org.example.model.User;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
        ProfileMenuController.changeAvatar(receivedPacket.getAttribute().get("new avatar path"), currentUser);
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
        hashMap.put("array list", new Gson().toJson(User.sortUsers()));
        Packet toBeSent = new Packet(ServerToClientCommands.SORTED_USERS.getCommand(),hashMap);
        connection.sendPacket(toBeSent);
    }

    public void connectionDatabase() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        HashMap<String, String> hashMap = new HashMap<>();
        if(ConnectionDatabase.getInstance().getConnectionByUsername(username) == null)
            hashMap.put("state", "offline");
        else hashMap.put("state", "online");
        Packet toBeSent = new Packet(ServerToClientCommands.CONNECTION.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void lastVisit() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("time", Long.toString(User.getUserByUsername(username).getLastLogout()));
        Packet toBeSent = new Packet("last visit", hashMap);
        connection.sendPacket(toBeSent);
    }

    public void getSearchedUsers() throws IOException {
        String search = receivedPacket.getAttribute().get("search");
        User searchedUser = ProfileMenuController.searchUsers(search);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user", new Gson().toJson(searchedUser));
        Packet toBeSent = new Packet(ServerToClientCommands.GET_SEARCHED_USERS.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void allConnections() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("array list", new Gson().toJson(ConnectionDatabase.getInstance().getSessionIdConnectionMap()));
        Packet toBeSent = new Packet(ServerToClientCommands.ALL_CONNECTIONS.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void getFriends() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("friends", new Gson().toJson(ProfileMenuController.getFriends(username)));
        Packet toBeSent = new Packet(ServerToClientCommands.FRIENDS_LIST.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void getPendingFriends() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pending", new Gson().toJson(ProfileMenuController.getPendingFriends(username)));
        Packet toBeSent = new Packet(ServerToClientCommands.PENDING_FRIENDS.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void requestFriend() throws IOException {
        String owner = receivedPacket.getAttribute().get("owner");
        String requester = receivedPacket.getAttribute().get("requester");
        String result = ProfileMenuController.requestFriend(owner, requester);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("result", result);
        Packet toBeSent = new Packet(ServerToClientCommands.REQUEST_FRIEND.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }
}
