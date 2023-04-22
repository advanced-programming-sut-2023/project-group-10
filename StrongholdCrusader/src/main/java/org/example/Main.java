package org.example;

import org.example.model.Stronghold;
import org.example.model.User;

import java.io.IOException;

public class Main {

    public static  void main(String[] args) throws IOException {
        User.loadUsersFromFile();
        //code
        if(Stronghold.getLoggedInUserFromFile() != null)
            Stronghold.setCurrentUser(Stronghold.getLoggedInUserFromFile());



    }
}