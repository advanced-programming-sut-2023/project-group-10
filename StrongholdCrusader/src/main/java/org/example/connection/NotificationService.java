package org.example.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NotificationService extends Thread {
    private final int port;

    public NotificationService(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String sessionID = dataInputStream.readUTF();
                ConnectionDatabase.getInstance().getConnectionBySessionId(sessionID).linkNotificationSocket(socket);
            }
        } catch (IOException e) {
            //TODO: try to reconnect...
        }
    }
}
