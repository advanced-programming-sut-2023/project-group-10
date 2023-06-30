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
            allPrivateChats = new Gson().fromJson(jsonString, new TypeToken<List<PrivateChat>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasAccess(String username) {
        return members.contains(username);
    }
}
