package org.example.model;

import com.google.gson.reflect.TypeToken;
import org.example.model.game.Battle;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.example.model.User.gson;

public class Stronghold {
    private static User currentUser;
    private static Battle currentBattle;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public static void setCurrentBattle(Battle newBattle) {
        currentBattle = newBattle;
    }
    public static User getLoggedInUserFromFile()  {

        try { String json = new String(Files.readAllBytes(Paths.get(" ./src/main/resources/LoggedInUser.json")));
            User loggedInUser = gson.fromJson(json, new TypeToken<User>() {
            }.getType());
            if(loggedInUser!=null)
                return loggedInUser;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void addUserToFile(User user){
        try {
            FileWriter fileWriter = new FileWriter(" ./src/main/resources/LoggedInUser.json");
            fileWriter.write(gson.toJson(user));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}