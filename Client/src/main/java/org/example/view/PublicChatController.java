package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicChatController {
    public Button add;
    public Button clear;
    public TextField messageField;
    public VBox chatBox;
    public ScrollPane chatScrollPane;
    String message;

    @FXML
    public void initialize() {
        //TODO put old messages,use process message func
        initChatBox();
        add.setOnMouseClicked(evt -> {

            message = messageField.getText();
            chatBox.getChildren().add(processMessage(message, true));
            chatScrollPane.setVvalue(1);

        });
        clear.setOnMouseClicked(evt -> {
            message = "";
            messageField.setText("");
        });

    }

    private void initChatBox() {
        //old chats
        chatScrollPane.setVvalue(1);
    }

    private HBox processMessage(String message, boolean isMine) {
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        VBox messagePane = new VBox();
        HBox newMessage = new HBox();
        Label senderId = new Label();
        //put Avatar-> I had errors
        ImageView avatar = new ImageView();
        Label content = new Label(message);
        Label time = new Label(strResult);
        //set time and format it
        messagePane.getChildren().addAll(senderId, content, time);

        newMessage.getChildren().addAll((Node) messagePane, (Node) avatar);
        return newMessage;

    }

}
