package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.LoginMenuController;
import org.example.view.enums.messages.LoginMenuMessages;

public class LoginMenu extends Application {

    public TextField username;
    public PasswordField password;
    public CheckBox stayLoggedIn;
    public Text usernameText;
    public Text passwordText;
    @FXML
    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new FXMLLoader(LoginMenu.class.getResource("/view/loginMenu.fxml")).load();
        Scene scene = new Scene(pane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.show();
    }

    public void forgetPassword(MouseEvent mouseEvent) {
        for(int i = 0; i < pane.getChildren().size(); i++){
            System.out.println(pane.getChildren().get(i));
        }
    }

    public void submit(MouseEvent mouseEvent) {
        LoginMenuMessages message = LoginMenuController.login(username.getText(), password.getText(), stayLoggedIn.isSelected());
        if(message.equals(LoginMenuMessages.USERNAME_DOESNT_EXIST)){
            usernameText.setText("username does not exist");
            passwordText.setText("");
        }
        else if(message.equals(LoginMenuMessages.WRONG_PASSWORD)){
            usernameText.setText("");
            passwordText.setText("wrong password");
        }

        //TODO login
    }
}
