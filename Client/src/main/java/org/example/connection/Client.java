package org.example.connection;

import javafx.stage.Stage;
import org.example.view.SignupMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private PacketParser packetParser;

    private static Client instance;

    private Client(String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        packetParser = new PacketParser();
        new SignupMenu().start(new Stage());
    }

    public static Client getInstance() throws Exception {
        if(instance == null) instance = new Client("localhost", 8080);
        return instance;
    }

    public void sendPacket(Packet packet) throws IOException {
        String gson = this.packetParser.parseGson(packet);
        this.dataOutputStream.writeUTF(gson);
    }

    public Packet recievePacket() throws IOException {
        String gson = this.dataInputStream.readUTF();
        return this.packetParser.parsePacket(gson);
    }
}
