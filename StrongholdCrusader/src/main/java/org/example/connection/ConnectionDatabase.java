package org.example.connection;

import java.util.HashMap;

public class ConnectionDatabase {
    private static ConnectionDatabase instance;
    private static HashMap<String, Connection> database;

    private ConnectionDatabase() {
    }

    public static ConnectionDatabase getInstance() {
        synchronized (instance) {
            if (instance == null) instance = new ConnectionDatabase();
        }
        return instance;
    }

    public void addConnection(String connectionID, Connection connection) {
        // TODO: check duplication
        database.put(connectionID, connection);
    }
}
