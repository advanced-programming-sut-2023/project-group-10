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

    public NotificationReceiver(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        packetParser = new PacketParser();
    }

    public void setChatController(ChatControllerParent chatController) {
        this.chatController = chatController;
    }

    @Override
    public void run() {
        // direct messages to different parts (just chat for now)
        while (true) {
            try {
                Packet packet = packetParser.parsePacket(dataInputStream.readUTF());
                ArrayList<Message> messages = new Gson().fromJson(packet.getAttribute().get("messages"), new TypeToken<List<Message>>() {
                }.getType());
                ServerToClientCommands command = ServerToClientCommands.getCommandByString(packet.command);
                switch (command) {
                    case GET_CHAT_MESSAGES:
                    case AUTO_UPDATE_CHAT_MESSAGES:
                        chatController.initChatBox(messages);
                        System.out.println(messages.get(messages.size() - 1).getMessageBody());
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
