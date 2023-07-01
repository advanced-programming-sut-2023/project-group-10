package org.example.connection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.chat.Message;
import org.example.view.chats.ChatControllerParent;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends Thread {
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final PacketParser packetParser;
    private ChatControllerParent chatController;
    private ArrayList<Message> messagesCache;

    public NotificationReceiver(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        packetParser = new PacketParser();
    }

    public void setChatController(ChatControllerParent chatController) {
        this.chatController = chatController;
    }

    public ArrayList<Message> getMessagesCache() {
        return messagesCache;
    }

    public void setMessagesCache(ArrayList<Message> messagesCache) {
        this.messagesCache = messagesCache;
    }

    @Override
    public void run() {
        // direct messages to different parts (just chat for now)
        while (true) {
            try {
                Packet packet = packetParser.parsePacket(dataInputStream.readUTF());
                ServerToClientCommands command = ServerToClientCommands.getCommandByString(packet.command);
                switch (command) {
                    case UNAUTHORIZED_REQUEST:
                        System.out.println("access denied");
                        System.exit(0);
                        break;
                    case CONNECTION_TIMED_OUT:
                        System.out.println("connection timed out");
                        System.exit(0);
                        break;
                    case GET_CHAT_MESSAGES:
                    case AUTO_UPDATE_CHAT_MESSAGES:
                        ArrayList<Message> messages = new Gson().fromJson(packet.getAttribute().get("messages"), new TypeToken<List<Message>>() {
                        }.getType());
                        messagesCache = messages;
                        break;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
