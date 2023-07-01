package org.example.connection;

import org.example.connection.Handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private final Socket socket;
    private Socket notificationSocket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private DataOutputStream notificationDataOutputStream;
    private final PacketParser packetParser;
    private final PacketHandler packetHandler;
    private String username;
    private String sessionId;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        generateAndSendSessionId(getInfo());
        packetParser = new PacketParser();
        packetHandler = new PacketHandler(this);
        // TODO: generate id
        ConnectionDatabase.getInstance().addConnection(sessionId, this);
        username = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                handleClient();
            }
        } catch (IOException e) {
        }
    }

    public void sendPacket(Packet toBeSentPacket) throws IOException {
        String json = packetParser.parseGson(toBeSentPacket);
        dataOutputStream.writeUTF(json);
    }

    public void sendNotification(Packet toBeSent) throws IOException {
        String json = packetParser.parseGson(toBeSent);
        notificationDataOutputStream.writeUTF(json);
    }

    public void handleClient() throws IOException {
        if (dataInputStream.available() != 0) {
            String input = dataInputStream.readUTF();
            Packet receivedPacket = packetParser.parsePacket(input);
            packetHandler.handle(receivedPacket);
        }
    }

    private String getInfo() throws IOException {
        return dataInputStream.readUTF();
    }

    private void generateAndSendSessionId(String info) throws IOException {
        //long time = Integer.parseInt(info);
        long time = 0;
        String sessionId = ConnectionDatabase.getInstance().getConnectionCount() + "_" + info + "_" + (time + 3600000);
        dataOutputStream.writeUTF(sessionId);
        this.sessionId = sessionId;
    }

    public void linkNotificationSocket(Socket socket) throws IOException {
        notificationSocket = socket;
        notificationDataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
}
