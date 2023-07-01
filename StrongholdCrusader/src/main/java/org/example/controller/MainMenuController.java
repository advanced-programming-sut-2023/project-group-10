package org.example.controller;

import javafx.scene.control.Alert;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.view.enums.messages.MainMenuMessages;

public class MainMenuController {
    public static void logout() {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername()))
            Stronghold.addUserToFile(null);
    }

    public static MainMenuMessages  startGame(){
        if (User.getUsers().size() < 2)
            return MainMenuMessages.INSUFFICIENT_GlOBAL_USERS;
        return MainMenuMessages.SUCCESS;
    }
}
