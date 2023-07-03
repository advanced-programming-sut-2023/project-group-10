package org.example.controller;

import org.example.model.User;
import org.example.model.lobby.Group;

import java.util.ArrayList;

public class LobbyController {
    public static Group createGroup(String groupName, User admin, int membersCap, boolean isPrivate) {
        Group newGroup = new Group(groupName, admin, membersCap, isPrivate);
        return newGroup;
    }

    public static ArrayList<Group> getTenRandomPublicGroups(User user) {
        ArrayList<Group> allGroups = Group.getAllGroups();
        ArrayList<Group> result;
        if (allGroups.size() <= 10) result = allGroups;
        else {
            result = new ArrayList<>();
            int count = 0;
            int allGroupsCount = allGroups.size();
            while (count < 10) {
                int nextIndex = (int) (Math.random() * allGroupsCount);
                if (allGroups.get(nextIndex).isPrivate() || result.contains(allGroups.get(nextIndex))) continue;
                result.add(allGroups.get(nextIndex));
                count++;
            }
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
