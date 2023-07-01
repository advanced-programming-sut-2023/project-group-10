package org.example.view.chats;

import org.example.model.chat.Message;

import java.util.ArrayList;

public interface ChatControllerParent {
    void initChatBox(ArrayList<Message> messages);
}
