package org.example.model.chat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Room extends Chat {
    private static ArrayList<Room> allRooms;
    private String adminUsername;
    private final ArrayList<String> membersUsernames;

    public Room(String chatID, String adminUsername) {
        super(chatID);
        this.adminUsername = adminUsername;
        membersUsernames = new ArrayList<>();
        membersUsernames.add(adminUsername);
    }

    public static ArrayList<Room> getAllRooms() {
        loadAllRoomsToApplication();
        return allRooms;
    }

    private static void loadAllRoomsToApplication() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("./StrongholdCrusader/src/main/resources/chatData/rooms.json")));
            allRooms = new Gson().fromJson(jsonString, new TypeToken<List<Room>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadChatToDatabase() {
        try {
            FileWriter fileWriter = new FileWriter("./StrongholdCrusader/src/main/resources/chatData/rooms.json");
            fileWriter.write(new Gson().toJson(allRooms, new TypeToken<List<Room>>() {
            }.getType()));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Room create(String admin, String roomID) {
        Room room = new Room(roomID, admin);
        getAllRooms().add(room);
        room.loadChatToDatabase();
        return room;
    }

    public void delete() {
        getAllRooms().remove(this);
        loadChatToDatabase();
    }

    public static Room getRoomById(String roomID) {
        ArrayList<Room> rooms = getAllRooms();
        for (Room room : rooms)
            if (room.getChatID().equals(roomID)) return room;
        return null;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public boolean isAdmin(String username) {
        return username.equals(adminUsername);
    }

    public ArrayList<String> getMembersUsernames() {
        return membersUsernames;
    }

    public void addToMembers(String username) {
        membersUsernames.add(username);
    }

    @Override
    public boolean hasAccess(String username) {
        return membersUsernames.contains(username);
    }
}
