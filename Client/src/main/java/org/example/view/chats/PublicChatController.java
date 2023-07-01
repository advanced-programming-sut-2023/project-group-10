package org.example.view.chats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.chat.Message;
import org.example.view.DataBank;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PublicChatController implements ChatControllerParent {
    public Button add;
    public Button clear;
    public TextField messageField;
    public VBox chatBox;
    public ScrollPane chatScrollPane;

    private ArrayList<Message> messagesCache;

    @FXML
    public void initialize() throws IOException {
        //TODO put old messages,use process message func
        messagesCache = getMessages();
        Client.getInstance().getNotificationReceiver().setMessagesCache(messagesCache);
        initChatBox(messagesCache);
        add.setOnMouseClicked(evt -> {
            try {
                sendMessage();
                messageField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            chatScrollPane.setVvalue(1);
        });
        clear.setOnMouseClicked(evt -> {
            messageField.setText("");
        });

        Timeline updateMessages = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<Message> notifiersCache = Client.getInstance().getNotificationReceiver().getMessagesCache();
            if (messagesCache != notifiersCache) {
                messagesCache = notifiersCache;
                if (messagesCache != null) initChatBox(messagesCache);
            }
        }));
        updateMessages.setCycleCount(Timeline.INDEFINITE);
        updateMessages.play();
    }

    private void sendMessage() throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("message body", messageField.getText());
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        attributes.put("time sent", strResult);
        attributes.put("chat type", "public");
        attributes.put("chat id", "public_chat");
        Packet packet = new Packet(ClientToServerCommands.SEND_MESSAGE.getCommand(), attributes);
        Client.getInstance().sendPacket(packet);
    }

    private ArrayList<Message> getMessages() throws IOException {
        Packet packet = new Packet(ClientToServerCommands.GET_PUBLIC_CHAT_MESSAGES.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        Packet receivedPacket = Client.getInstance().recievePacket();
        return new Gson().fromJson(receivedPacket.getAttribute().get("messages"), new TypeToken<List<Message>>() {
        }.getType());
    }

    public void initChatBox(ArrayList<Message> messages) {
        chatBox.getChildren().clear();
        for (Message msg : messages)
            chatBox.getChildren().add(processMessage(msg));
        chatScrollPane.setVvalue(1);
    }

    private HBox processMessage(Message message) {
        boolean isMine = message.getSender().equals(DataBank.getLoggedInUser());
        String strResult = message.getTimeSent();
        VBox messagePane = new VBox();
        HBox newMessage = new HBox();
        Label senderId = new Label(message.getSender().getUsername());
        //put Avatar-> I had errors
        Rectangle avatar = new Rectangle();
        Label content = new Label(message.getMessageBody());
        Label time = new Label(strResult);
        //set time and format it
        messagePane.getChildren().addAll(senderId, content, time);
        if (isMine) {
            Button edit = new Button("edit");
            Button deleteForMe = new Button("del:m");
            Button deleteForEveryOn = new Button("del:e");
            edit.setOnMouseClicked(this::editMessage);
            deleteForMe.setOnMouseClicked(this::deleteForMe);
            deleteForEveryOn.setOnMouseClicked(this::deleteForEveryOne);
            messagePane.getChildren().addAll();
            newMessage.getChildren().addAll(messagePane, avatar);
        } else

            newMessage.getChildren().addAll(avatar, messagePane);
        newMessage.setId(message.getMessageID());
        return newMessage;

    }

    private void deleteForEveryOne(MouseEvent mouseEvent) {
        String messageId=((Button) mouseEvent.getSource()).getParent().getId();
        HashMap<String,String> attributes=new HashMap<>();
        //    DELETE_MESSAGE("delete message", "message id", "chat type", "chat id")
        attributes.put("message id",messageId);
        attributes.put("chat type","public");
        Packet packet = new Packet(ClientToServerCommands.EDIT_MESSAGE.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            initChatBox(getMessages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteForMe(MouseEvent mouseEvent) {
        String messageId=((Button) mouseEvent.getSource()).getParent().getId();
        try {
            ArrayList<Message> messages=getMessages();
            int index=0;
            for(int i=0;i<messages.size();i++){
                if (messages.get(i).getMessageID().equals(messageId))
                    index=i;
            }
            messages.remove(index);
            initChatBox(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void editMessage(MouseEvent mouseEvent) {
        String messageId=((Button) mouseEvent.getSource()).getParent().getId();
        HashMap<String,String> attributes=new HashMap<>();
        //    EDIT_MESSAGE("edit message", "message id", "chat type", "chat id", "new body");
        attributes.put("message id",messageId);
        attributes.put("chat type","public");
        attributes.put("new body",messageField.getText());
        Packet packet = new Packet(ClientToServerCommands.EDIT_MESSAGE.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            initChatBox(getMessages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        new ChatMenuHomeGFX().start(SignupMenu.stage);
    }
}
