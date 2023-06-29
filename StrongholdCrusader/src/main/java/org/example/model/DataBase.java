package org.example.model;

import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.example.model.User.getUsers;
import static org.example.model.User.gson;

public class DataBase {
    public void loadUsersFromFile() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./StrongholdCrusader/src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) {
                User.clearUsers();
                User.updateUsers(createdUsers);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveUsersToFile() {
        try {
            FileWriter fileWriter = new FileWriter("./StrongholdCrusader/src/main/resources/UserDatabase.json");
            fileWriter.write(gson.toJson(getUsers()));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
