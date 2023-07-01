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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrivateChatController {
    public Rectangle avatar;
    public Label nicknameLabel;
    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public TextField messageField;
    public Button add;
    public Button clear;
    public HBox idBox;
    private String message;

    @FXML
    public void initialize() {
        initID();
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

    private void initID() {

    }

    private void initChatBox() {
        //old chats
        chatScrollPane.setVvalue(1);
    }

    private VBox processMessage(String message, boolean isMine) {
        Format f = new SimpleDateFormat("HH:mm");
        String strResult = f.format(new Date());
        VBox messagePane = new VBox();
        Label content = new Label(message);
        Label time = new Label(strResult);
        messagePane.getChildren().addAll(content, time);

        if (isMine) {
            Button edit = new Button("edit");
            Button deleteForMe = new Button("del:m");
            Button deleteForEveryOne = new Button("del:e");
            edit.setOnMouseClicked(this::editMessage);
            deleteForMe.setOnMouseClicked(this::deleteForMe);
            deleteForEveryOne.setOnMouseClicked(this::deleteForEveryOne);
            HBox buttons = new HBox();
            buttons.getChildren().addAll(edit, deleteForMe, deleteForEveryOne);
            messagePane.getChildren().addAll(messagePane, buttons);
        }
        return messagePane;

    }

    private void deleteForEveryOne(MouseEvent mouseEvent) {
    }

    private void deleteForMe(MouseEvent mouseEvent) {
    }

    private void editMessage(MouseEvent mouseEvent) {
    }
}
