package org.example.model.lobby;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.ConnectionDatabase;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.ChatController;
import org.example.model.User;
import org.example.model.chat.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Group {
    private final long TIMEOUT_LIMIT = 300000;
    private final static ArrayList<Group> allGroups = new ArrayList<>();
    private final String groupId;
    private final String groupName;
    private User admin;
    private ArrayList<User> members;
    private boolean isPrivate;
    private int membersCap;
    private transient final Timer expirationTimer;
    private transient final ArrayList<User> listWatchers;
    private transient final Room room;

    public Group(String groupName, User admin, int membersCap, boolean isPrivate) {
        this.groupName = groupName;
        this.admin = admin;
        members = new ArrayList<>();
        members.add(admin);
        this.isPrivate = isPrivate;
        this.membersCap = membersCap;
        synchronized (Group.class) {
            this.groupId = groupName + System.currentTimeMillis();
        }
        expirationTimer = new Timer();
        expirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                deleteGroup();
                sendTimeOutToMembers();
                informWatchers();
            }
        }, TIMEOUT_LIMIT);
        listWatchers = new ArrayList<>();
        room = Room.create(admin.getUsername(), groupId);
        allGroups.add(this);
    }

    public static ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public static ArrayList<Group> getAllPublicGroups() {
        ArrayList<Group> publicGroups = new ArrayList<>();
        for (Group group : allGroups)
            if (!group.isPrivate) publicGroups.add(group);
        return publicGroups;
    }

    public static Group getGroupById(String groupId) {
        for (Group group : allGroups)
            if (group.groupId.equals(groupId)) return group;
        return null;
    }

    public String getGroupId() {
        return groupId;
    }

    public User getAdmin() {
        return admin;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        if (isPrivate) {
            for (User listWatcher : listWatchers)
                listWatcher.getViewingGroupsInList().remove(this);
            informWatchers();
            listWatchers.clear();
        }
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void addWatcher(User watcher) {
        listWatchers.add(watcher);
    }

    public void removeWatcher(User watcher) {
        listWatchers.remove(watcher);
    }

    public void informWatchers() {
        for (User watcher : listWatchers) {
            Connection connection = ConnectionDatabase.getInstance().getConnectionByUsername(watcher.getUsername());
            if (connection == null) continue;
            HashMap<String, String> groupListAttributes = new HashMap<>();
            groupListAttributes.put("groups", new Gson().toJson(watcher.getViewingGroupsInList()));
            Packet toBeSent = new Packet(ServerToClientCommands.GROUP_LIST_REFRESHED.getCommand(), groupListAttributes);
            try {
                connection.sendNotification(toBeSent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void informMembers() {
        HashMap<String, String> groupInfoAttributes = new HashMap<>();
        groupInfoAttributes.put("group object", new Gson().toJson(this));
        for (User member : members) {
            Connection connection = ConnectionDatabase.getInstance().getConnectionByUsername(member.getUsername());
            if (connection == null) continue;
            Packet toBeSent = new Packet(ServerToClientCommands.GROUP_INFO_REFRESHED.getCommand(), groupInfoAttributes);
            try {
                connection.sendNotification(toBeSent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void addMember(User user) {
        listWatchers.remove(user);
        members.add(user);
        ChatController.addMemberToRoom(room.getChatID(), user.getUsername());
        if (members.size() == membersCap) {
            startGameSequence();
        } else {
            expirationTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    deleteGroup();
                    sendTimeOutToMembers();
                    informWatchers();
                }
            }, TIMEOUT_LIMIT);
            informMembers();
            informWatchers();
        }
    }

    public synchronized void removeMember(User user) {
        members.remove(user);
        if (members.size() == 0) {
            deleteGroup();
            informWatchers();
        } else {
            if (user.equals(admin)) {
                admin = members.get(0);
                room.setAdminUsername(admin.getUsername());
            }
            informMembers();
            informWatchers();
        }
    }

    private void deleteGroup() {
        room.delete();
        for (User listWatcher : listWatchers)
            listWatcher.getViewingGroupsInList().remove(this);
        allGroups.remove(this);
    }

    private void sendTimeOutToMembers() {
        Packet toBeSent = new Packet(ServerToClientCommands.GROUP_TIMED_OUT.getCommand(), null);
        for (User member : members)
            try {
                ConnectionDatabase.getInstance().getConnectionByUsername(member.getUsername()).sendNotification(toBeSent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private void startGameSequence() {
        Packet toBeSent = new Packet(ServerToClientCommands.Group_IS_COMPLETE.getCommand(), null);
        for (User member : members) {
            Connection connection = ConnectionDatabase.getInstance().getConnectionByUsername(member.getUsername());
            if (connection != null) {
                try {
                    connection.sendNotification(toBeSent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        deleteGroup();
        informWatchers();
    }

    public void startGameEarly() {
        membersCap = members.size();
        startGameSequence();
    }
}
