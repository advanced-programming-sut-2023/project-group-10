package org.example.connection;

import org.example.view.DataBank;
import org.example.view.SignupMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private PacketParser packetParser;
    private String sessionID;

    private static Client instance;

    public Client(String host, int port) {
        try {
            instance = this;
            Socket socket = new Socket(host, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(String.valueOf(System.currentTimeMillis()));
            sessionID = dataInputStream.readUTF();
            openNotification();
            packetParser = new PacketParser();
            String[] strings = new String[]{"a", "b"};
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(SignupMenu.class);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openNotification() throws IOException {
        Socket notificationSocket = new Socket("localhost", 8081);
        new DataOutputStream(notificationSocket.getOutputStream()).writeUTF(sessionID);
        new NotificationReceiver(notificationSocket).start();
    }

    public static Client getInstance() {
        if (instance == null) instance = new Client("localhost", 8080);
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

    public void logout() {
        DataBank.setLoggedInUser(null);
    }
}
