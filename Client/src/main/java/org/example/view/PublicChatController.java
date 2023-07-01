package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

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
        Rectangle avatar = new Rectangle();
        Label content = new Label(message);
        Label time = new Label(strResult);
        //set time and format it
        messagePane.getChildren().addAll(senderId, content, time);
        if (isMine) {
            Button edit=new Button("edit");
            Button deleteForMe=new Button("del:m");
            Button deleteForEveryOn=new Button("del:e")
            edit.setOnMouseClicked(this::editMessage);
            deleteForMe.setOnMouseClicked(this::deleteForMe);
            deleteForEveryOn.setOnMouseClicked(this::deleteForEveryOne);
            messagePane.getChildren().addAll();
            newMessage.getChildren().addAll(messagePane, avatar);
        } else

            newMessage.getChildren().addAll(avatar, messagePane);
        return newMessage;

    }

    private void deleteForEveryOne(MouseEvent mouseEvent) {
    }

    private void deleteForMe(MouseEvent mouseEvent) {
    }

    private void editMessage(MouseEvent mouseEvent) {
    }

}
