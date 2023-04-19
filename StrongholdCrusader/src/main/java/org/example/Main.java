package org.example;

import  org.example.model.User;

public class Main {

    public static  void main(String[] args) {
        User.loadUsersFromFile();
        User.addUser("yasna","yasna2004","morgh","yas","goshname");

    }
}