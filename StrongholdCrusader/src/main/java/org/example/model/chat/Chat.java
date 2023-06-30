package org.example.model.chat;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.ConnectionDatabase;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Chat {
    private final String chatID;
    private final ArrayList<Message> messages;
    private final ArrayList<String> watchersUsernames;

    public Chat(String chatID) {
        this.chatID = chatID;
        messages = new ArrayList<>();
        watchersUsernames = new ArrayList<>();
    }

    public String getChatID() {
        return chatID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message newMessage) {
        messages.add(newMessage);
        loadChatToDatabase();
        informWatchers();
    }

    public void addWatcher(String username) {
        watchersUsernames.add(username);
    }

    // TODO: move to controller/handler
    public void informWatchers() {
        for (String username : watchersUsernames) {
            Connection connection = ConnectionDatabase.getInstance().getConnectionByUsername(username);
            if (connection != null) {
                String messagesJson = new Gson().toJson(messages, new TypeToken<List<Message>>() {
                }.getType());
                Packet toBeSent = new Packet(ServerToClientCommands.AUTO_UPDATE_CHAT_MESSAGES.getCommand(), (HashMap<String, String>) Map.of("messages", messagesJson));
                try {
                    connection.sendPacket(toBeSent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public abstract void loadChatToDatabase();

    public abstract boolean hasAccess(String username);

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chat)) return false;
        return ((Chat) obj).getChatID().equals(chatID);
    }

    public void exitChat(String username) {
        watchersUsernames.remove(username);
    }
}
