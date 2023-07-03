package org.example.connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.chat.Message;
import org.example.model.lobby.Group;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends Thread {
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final PacketParser packetParser;
    private ArrayList<Message> messagesCache;
    private ArrayList<String> chatListCache;
    private int userStateChange = 0;
    private ArrayList<Group> groupListCache;
    private Group groupCache;
    private boolean isGroupComplete = false;

    public NotificationReceiver(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        packetParser = new PacketParser();
    }

    public ArrayList<Message> getMessagesCache() {
        return messagesCache;
    }

    public void setMessagesCache(ArrayList<Message> messagesCache) {
        this.messagesCache = messagesCache;
    }

    public ArrayList<String> getChatListCache() {
        return chatListCache;
    }

    public void setChatListCache(ArrayList<String> chatListCache) {
        this.chatListCache = chatListCache;
    }

    public int getUserStateChange() {
        return userStateChange;
    }

    public ArrayList<Group> getGroupListCache() {
        return groupListCache;
    }

    public void setGroupListCache(ArrayList<Group> groupListCache) {
        this.groupListCache = groupListCache;
    }

    public Group getGroupCache() {
        return groupCache;
    }

    public void setGroupCache(Group groupCache) {
        this.groupCache = groupCache;
    }

    public boolean isGroupComplete() {
        return isGroupComplete;
    }

    public void setGroupComplete(boolean groupComplete) {
        isGroupComplete = groupComplete;
    }

    @Override
    public void run() {
        // direct messages to different parts (just chat for now)
        while (true) {
            try {
                Packet packet = packetParser.parsePacket(dataInputStream.readUTF());
                ServerToClientCommands command = ServerToClientCommands.getCommandByString(packet.command);
                switch (command) {
                    case UNAUTHORIZED_REQUEST:
                        System.out.println("access denied");
                        System.exit(0);
                        break;
                    case CONNECTION_TIMED_OUT:
                        System.out.println("connection timed out");
                        System.exit(0);
                        break;
                    case GET_CHAT_MESSAGES:
                    case AUTO_UPDATE_CHAT_MESSAGES:
                        messagesCache = new Gson().fromJson(packet.getAttribute().get("messages"), new TypeToken<List<Message>>() {
                        }.getType());
                        break;
                    case GET_CHAT_LIST:
                        chatListCache = new Gson().fromJson(packet.getAttribute().get("chats"), new TypeToken<List<String>>() {
                        }.getType());
                        break;
                    case LOGGED_OUT:
                    case LOGIN:
                        userStateChange++;
                        break;
                    case GROUP_LIST_REFRESHED:
                        groupListCache = new Gson().fromJson(packet.getAttribute().get("groups"), new TypeToken<List<Group>>() {
                        }.getType());
                        break;
                    case GROUP_INFO_REFRESHED:
                        groupCache = new Gson().fromJson(packet.getAttribute().get("group object"), Group.class);
                        break;
                    case GROUP_TIMED_OUT:
                        groupCache = null;
                        break;
                    case Group_IS_COMPLETE:
                        isGroupComplete = true;
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
