package org.example.connection;

import java.util.HashMap;
import java.util.Map;

public class ConnectionDatabase {
    private HashMap<String, Connection> sessionIdConnectionMap = new HashMap<>();

    public int getConnectionCount() {
        return sessionIdConnectionMap.size();
    }

    private static class Holder {
        private static final ConnectionDatabase INSTANCE = new ConnectionDatabase();
    }

    private ConnectionDatabase() {
    }

    public static ConnectionDatabase getInstance() {
        return Holder.INSTANCE;
    }

    public HashMap<String, Connection> getSessionIdConnectionMap() {
        return sessionIdConnectionMap;
    }

    public Connection getConnectionByUsername(String username) {
        for (Map.Entry<String, Connection> entry : sessionIdConnectionMap.entrySet())
            if (entry.getValue().getUsername().equals(username)) return entry.getValue();
        return null;
    }

    public Connection getConnectionBySessionId(String sessionId) {
        return sessionIdConnectionMap.get(sessionId);
    }

    public void addConnection(String sessionId, Connection connection) {
        sessionIdConnectionMap.put(sessionId, connection);
    }
}
