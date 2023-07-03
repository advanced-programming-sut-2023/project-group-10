package org.example.connection.Handlers;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.LobbyController;
import org.example.model.User;
import org.example.model.lobby.Group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public LobbyHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void createGroup() throws IOException {
        User admin = User.getUserByUsername(connection.getUsername());
        String groupName = receivedPacket.getAttribute().get("group name");
        int membersCap = Integer.parseInt(receivedPacket.getAttribute().get("player count"));
        boolean isPrivate = Boolean.parseBoolean(receivedPacket.getAttribute().get("is private"));
        Group newGroup = LobbyController.createGroup(groupName, admin, membersCap, isPrivate);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("group object", new Gson().toJson(newGroup));
        Packet toBeSent = new Packet(ServerToClientCommands.GROUP_CREATED.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void refreshGroupList() throws IOException {
        ArrayList<Group> tenRandomGroups = LobbyController.getTenRandomPublicGroups(User.getUserByUsername(connection.getUsername()));
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("groups", new Gson().toJson(tenRandomGroups));
        Packet toBeSent = new Packet(ServerToClientCommands.GROUP_LIST_REFRESHED.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void joinGroup() throws IOException {
        User user = User.getUserByUsername(connection.getUsername());
        Group targetGroup = Group.getGroupById(receivedPacket.getAttribute().get("group id"));
        if (targetGroup != null)
            LobbyController.joinGroup(user, targetGroup);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("group object", new Gson().toJson(targetGroup));
        Packet toBeSent = new Packet(ServerToClientCommands.GROUP_INFO_REFRESHED.getCommand(), attributes);
        connection.sendPacket(toBeSent);
    }

    public void leaveGroup() {
        User user = User.getUserByUsername(connection.getUsername());
        String groupId = receivedPacket.getAttribute().get("group id");
        LobbyController.leaveGroup(user, groupId);
    }

    public void startEarly() {
        User user = User.getUserByUsername(connection.getUsername());
        Group group = Group.getGroupById(receivedPacket.getAttribute().get("group id"));
        if (group != null && group.getAdmin().equals(user))
            group.startGameEarly();
    }

    public void changeGroupPrivacy() {
        User user = User.getUserByUsername(connection.getUsername());
        boolean isPrivate = Boolean.parseBoolean(receivedPacket.getAttribute().get("is private"));
        Group group = Group.getGroupById(receivedPacket.getAttribute().get("group id"));
        if (group != null && group.getAdmin().equals(user))
            group.setPrivate(isPrivate);
    }
}
