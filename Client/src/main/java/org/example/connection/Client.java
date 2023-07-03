package org.example.connection;

import org.example.view.DataBank;
import org.example.view.SignupMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private PacketParser packetParser;
    private String sessionID;
    private NotificationReceiver notificationReceiver;

    private static Client instance;

    private Client(String host, int port) {
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
        Socket notificationSocket = new Socket("localhost", 8082);
        new DataOutputStream(notificationSocket.getOutputStream()).writeUTF(sessionID);
        notificationReceiver = new NotificationReceiver(notificationSocket);
        notificationReceiver.start();
    }

    public static Client getInstance() {
        if (instance == null) instance = new Client("localhost", 8081);
        return instance;
    }

    public NotificationReceiver getNotificationReceiver() {
        return notificationReceiver;
    }

    public void sendPacket(Packet packet) throws IOException {
        if (packet.getAttribute() == null) {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("sessionID", sessionID);
            packet.setAttribute(attributes);
        } else packet.getAttribute().put("sessionID", sessionID);
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
