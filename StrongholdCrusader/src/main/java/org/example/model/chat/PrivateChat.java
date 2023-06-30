package org.example.model.chat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat extends Chat {
    private static ArrayList<PrivateChat> allPrivateChats;
    private final ArrayList<String> members;

    public PrivateChat(String chatID, String member1, String member2) {
        super(chatID);
        members = new ArrayList<>(2);
        members.add(member1);
        members.add(member2);
    }

    public static ArrayList<PrivateChat> getAllPrivateChats() {
        loadAllPrivateChatsToApplication();
        return allPrivateChats;
    }

    private static void loadAllPrivateChatsToApplication() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("./StrongholdCrusader/src/main/resources/chatData/privateChats.json")));
            allPrivateChats = new Gson().fromJson(jsonString, new TypeToken<List<PrivateChat>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateChat getPrivateChatByMembers(String member1, String member2) {
        ArrayList<PrivateChat> privateChats = getAllPrivateChats();
        for (PrivateChat privateChat : privateChats)
            if (privateChat.hasAccess(member1) && privateChat.hasAccess(member2)) return privateChat;
        return null;
    }

    public String getOtherPartyUsername(String username) {
        if (members.get(0).equals(username)) return members.get(1);
        else return members.get(0);
    }

    @Override
    public boolean hasAccess(String username) {
        return members.contains(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrivateChat)) return false;
        PrivateChat otherChat = (PrivateChat) obj;
        return otherChat.hasAccess(members.get(0)) && otherChat.hasAccess(members.get(1));
    }
}
