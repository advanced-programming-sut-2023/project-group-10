package org.example.model.chat;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PublicChat extends Chat {
    private static PublicChat instance;

    private PublicChat() {
        super("public_chat");
    }

    public static PublicChat getInstance() {
        loadPublicChatToApplication();
        return instance;
    }

    private static void loadPublicChatToApplication() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("./StrongholdCrusader/src/main/resources/chatData/publicChat.json")));
            instance = new Gson().fromJson(jsonString, PublicChat.class);
            if (instance == null) {
                instance = new PublicChat();
                instance.loadChatToDatabase();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadChatToDatabase() {
        try {
            FileWriter fileWriter = new FileWriter("./StrongholdCrusader/src/main/resources/chatData/publicChat.json");
            fileWriter.write(new Gson().toJson(instance, PublicChat.class));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean hasAccess(String username) {
        return true;
    }
}
