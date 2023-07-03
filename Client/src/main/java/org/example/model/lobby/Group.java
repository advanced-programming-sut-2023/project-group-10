package org.example.model.lobby;

import org.example.model.User;

import java.util.ArrayList;

public class Group {
    private final static ArrayList<Group> allGroups = new ArrayList<>();
    private String groupId;
    private String groupName;
    private User admin;
    private ArrayList<User> members;
    private boolean isPrivate;
    private int membersCap;

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getMembersCap() {
        return membersCap;
    }
}
