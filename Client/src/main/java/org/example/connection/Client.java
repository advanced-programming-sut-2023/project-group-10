package org.example.connection;

import org.example.view.SignupMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private PacketParser packetParser;

    private static Client instance;

    public Client(String host, int port)  {
        try {
            Socket socket = new Socket(host, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            packetParser = new PacketParser();
            String[] strings = new String[]{"a", "b"};
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(SignupMenu.class);
                }
            }.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Client getInstance() {
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
