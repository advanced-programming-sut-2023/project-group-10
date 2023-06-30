package org.example.connection;

import java.util.HashMap;

public class ConnectionDatabase {
    private HashMap<String, Connection> database;

    private static class Holder {
        private static final ConnectionDatabase INSTANCE = new ConnectionDatabase();
    }

    private ConnectionDatabase() {
    }

    public static ConnectionDatabase getInstance() {
        return Holder.INSTANCE;
    }

    public HashMap<String, Connection> getDatabase() {
        return database;
    }

    public void addConnection(String connectionID, Connection connection) {
        // doesn't check duplicate id
    }
}
