package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Stronghold;

public class ProfileMenu2 extends Application {
    public Circle avatar;
    public Text username;
    public Text password;
    public Text nickname;
    public Text email;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new FXMLLoader(ProfileMenu2.class.getResource("/view/profileMenu.fxml")).load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    public void initialize(){
        //TODO set avatar
        /*username.setText(Stronghold.getCurrentUser().getUsername());
        password.setText(Stronghold.getCurrentUser().getPassword());
        nickname.setText(Stronghold.getCurrentUser().getNickname());
        email.setText(Stronghold.getCurrentUser().getEmail());*/
    }

    public void changeUsername() {
        Pane pane = new Pane();

    }

    public void changePassword(){

    }

    public void changeNickname(){

    }

    public void changeEmail(){

    }

    public void changeAvatar(){

    }
}
