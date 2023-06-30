package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrivateChatsHomeController {
    public TextField searchBox;
    public Button searchButton;
    public ListView<Label> chats;
    String username;

    @FXML
    public void initialize(){
        //TODO put old messages,use process message func
        initChatBox();
        searchButton.setOnMouseClicked(evt->{

           username=searchBox.getText();
           addNewChat(searchBox.getText());
        });

    }

    private void initChatBox() {

    }

    private void addNewChat(String username){
        //if it was successful invoke process chat
    }

    private void processChat(String username){
        //add a child to list view-> add onMouseClicked for every label of the ListView
    }



}
