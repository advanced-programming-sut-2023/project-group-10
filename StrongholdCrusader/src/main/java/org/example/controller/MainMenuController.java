package org.example.controller;

import org.example.model.Stronghold;
import org.example.view.enums.messages.MainMenuMessages;

public class MainMenuController {
    public static MainMenuMessages logout() {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername()))
            Stronghold.addUserToFile(null);

        return MainMenuMessages.SUCCESSFUL_LOGOUT;
    }
}
