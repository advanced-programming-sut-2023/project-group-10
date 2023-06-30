package org.example.connection;

import org.example.connection.Handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final PacketParser packetParser;
    private final PacketHandler packetHandler;
    private String username;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        packetParser = new PacketParser();
        packetHandler = new PacketHandler(this);
        // TODO: generate id
        String connectionID = "";
        ConnectionDatabase.getInstance().addConnection(connectionID, this);
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

    public void handleClient() throws IOException {
        if(dataInputStream.available() != 0){
            String input = dataInputStream.readUTF();
            Packet receivedPacket = packetParser.parsePacket(input);
            packetHandler.handle(receivedPacket);
        }
    }
}
