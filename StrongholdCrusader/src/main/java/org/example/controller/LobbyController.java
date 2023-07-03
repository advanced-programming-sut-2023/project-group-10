package org.example.controller;

import org.example.model.User;
import org.example.model.lobby.Group;

import java.util.ArrayList;

public class LobbyController {
    public static Group createGroup(String groupName, User admin, int membersCap, boolean isPrivate) {
        return new Group(groupName, admin, membersCap, isPrivate);
    }

    public static ArrayList<Group> getTenRandomPublicGroups(User user) {
        ArrayList<Group> allGroups = Group.getAllPublicGroups();
        ArrayList<Group> result;
        int limit = Math.min(allGroups.size(), 10);
        result = new ArrayList<>();
        int count = 0;
        int allGroupsCount = allGroups.size();
        while (count < limit) {
            int nextIndex = (int) (Math.random() * allGroupsCount);
            if (allGroups.get(nextIndex).isPrivate() || result.contains(allGroups.get(nextIndex))) continue;
            result.add(allGroups.get(nextIndex));
            count++;
        }
        user.updateViewingGroup(result);
        return result;
    }

    public static void joinGroup(User user, Group targetGroup) {
        user.updateViewingGroup(null);
        targetGroup.addMember(user);
    }

    public static void leaveGroup(User user, String groupId) {
        Group targetGroup = Group.getGroupById(groupId);
        targetGroup.removeMember(user);
    }
}
