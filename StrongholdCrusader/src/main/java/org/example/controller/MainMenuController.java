package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Color;
import org.example.view.enums.messages.MainMenuMessages;

import java.util.HashMap;

public class MainMenuController {

    public static MainMenuMessages checkMapAndGovernmentsCount(int mapSize, int governmentCount) {
        if (mapSize != 200 && mapSize != 400)
            return MainMenuMessages.INVALID_MAP_SIZE;
        if (governmentCount < 2 || governmentCount > 8 )
            return MainMenuMessages.INVALID_GOVERNMENT_COUNT;
        if( governmentCount > User.getUsers().size())
            return MainMenuMessages.INSUFFICIENT_GlOBAL_USERS;
        return MainMenuMessages.SUCCESS;
    }

    public static MainMenuMessages logout() {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername()))
            Stronghold.addUserToFile(null);

        return MainMenuMessages.SUCCESSFUL_LOGOUT;
    }

    public static MainMenuMessages getPlayers(String username, String color, HashMap<String,String> players) {
        User myUser = User.getUserByUsername(username);
        if (myUser == null)
            return MainMenuMessages.INVALID_USERNAME;
        if(players.containsKey(username))
            return MainMenuMessages.USER_IN_THE_BATTLE;

        Color myColor = MainMenuController.isColorValid(color);
        if (myColor == null) {
            return MainMenuMessages.INVALID_COLOR;
        }
        if(players.containsValue(myColor.getName()))
            return MainMenuMessages.TAKEN_COLOR;

        return MainMenuMessages.SUCCESS;
    }

    public static Color isColorValid(String color) {
        for (Color color1 : Color.values()) {
            if (color1.getName().equals(color))
                return color1;
        }
        return null;
    }
}
