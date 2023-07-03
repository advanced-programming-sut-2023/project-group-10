package org.example;

import org.example.connection.NotificationService;
import org.example.connection.Server;

public class Main {
    public static void main(String[] args) {
        new NotificationService(8082).start();
        new Server(8081);
    }
}
