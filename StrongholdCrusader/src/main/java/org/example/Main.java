package org.example;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.view.MainMenu;
import org.example.view.SignupMenu;

import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        User.loadUsersFromFile();
        //code
        if (Stronghold.getLoggedInUserFromFile() != null) {
            Stronghold.setCurrentUser(Stronghold.getLoggedInUserFromFile());
            MainMenu.run();
        } else SignupMenu.run();
    }
}