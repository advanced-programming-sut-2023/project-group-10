package org.example.connection;

import java.util.HashMap;
import java.util.Map;

public class ConnectionDatabase {
    private HashMap<String, Connection> connectionIdConnectionMap;

    private static class Holder {
        private static final ConnectionDatabase INSTANCE = new ConnectionDatabase();
    }

    private ConnectionDatabase() {
    }

    public static ConnectionDatabase getInstance() {
        return Holder.INSTANCE;
    }

    public HashMap<String, Connection> getConnectionIdConnectionMap() {
        return connectionIdConnectionMap;
    }

    public Connection getConnectionByUsername(String username) {
        for (Map.Entry<String, Connection> entry : connectionIdConnectionMap.entrySet())
            if (entry.getValue().getUsername().equals(username)) return entry.getValue();
        return null;
    }

    public void addConnection(String connectionID, Connection connection) {
        // doesn't check duplicate id
    }
}
