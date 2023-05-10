package org.example;

import org.example.model.Stronghold;
import org.example.view.LoginMenu;
import org.example.view.MainMenu;
import org.example.view.SignupMenu;

public class Main {
    public static void main(String[] args) {
        Stronghold.initializeApp();
        if (Stronghold.getLoggedInUserFromFile() != null) {
            Stronghold.setCurrentUser(Stronghold.getLoggedInUserFromFile());
            MainMenu.run();
            SignupMenu.run();
        } else SignupMenu.run();
    }
}