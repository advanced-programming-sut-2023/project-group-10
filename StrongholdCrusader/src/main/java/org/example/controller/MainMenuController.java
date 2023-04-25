package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Color;
import org.example.view.enums.messages.MainMenuMessages;

public class MainMenuController {
    public static MainMenuMessages startGame(int mapSize, int governmentCount, String[] usernames, Color[] colors) {
        if (mapSize != 200 && mapSize != 400)
            return MainMenuMessages.INVALID_MAP_SIZE;
        if (governmentCount < 2 || governmentCount > 8)
            return MainMenuMessages.INVALID_GOVERNMENT_COUNT;
        //TODO: implement it in a way which tells which id is incorrect
        for (String username : usernames) {
            if (User.getUserByUsername(username) == null)
                return MainMenuMessages.INVALID_USERNAME;
        }
        //TODO: check it
        for (Color color : colors) {
            for (Color value : Color.values()) {
                if (false)
                    return MainMenuMessages.INVALID_COLOR;
            }
        }

        return MainMenuMessages.SUCCESS;
    }

    public static MainMenuMessages logout() {
        if (Stronghold.getCurrentBattle() != null &&
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().equals(Stronghold.getCurrentUser()))
            return MainMenuMessages.USER_IN_THE_BATTLE;
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername()))
            Stronghold.addUserToFile(null);

        return MainMenuMessages.SUCCESSFUL_LOGOUT;
    }

    public static Color isColorValid(String color) {
        for (Color color1 : Color.values()) {
            if (color1.getName().equals(color))
                return color1;
        }
        return null;
    }
}
