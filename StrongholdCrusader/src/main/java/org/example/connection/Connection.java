package org.example.connection;

import org.example.connection.Handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    PacketParser packetParser;
    PacketHandler packetHandler;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        packetParser = new PacketParser();
        packetHandler = new PacketHandler(this);
        // TODO: generate id
        String connectionID = "";
        ConnectionDatabase.getInstance().addConnection(connectionID, this);
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                String input = dataInputStream.readUTF();
                Packet receivedPacket = packetParser.parsePacket(input);
                packetHandler.handle(receivedPacket);
            }
        } catch (IOException e) {
        }
    }

    public void sendPacket(Packet toBeSentPacket) throws IOException {
        String json = packetParser.parseGson(toBeSentPacket);
        dataOutputStream.writeUTF(json);
    }
}
