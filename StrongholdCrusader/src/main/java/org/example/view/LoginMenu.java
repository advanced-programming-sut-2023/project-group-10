package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.LoginMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.User;
import org.example.view.enums.messages.LoginMenuMessages;

public class LoginMenu extends Application {

    public TextField username;
    public PasswordField password;
    public CheckBox stayLoggedIn;
    public Text usernameText;
    public Text passwordText;
    @FXML
    private Pane pane;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = new FXMLLoader(LoginMenu.class.getResource("/view/loginMenu.fxml")).load();
        Scene scene = new Scene(pane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.show();
    }

    public void forgetPassword() {
        pane.getChildren().remove(0);
        Pane main = new Pane();
        main.setTranslateX(100);
        main.setTranslateY(100);

        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        TextField username = new TextField();
        username.setPromptText("username");
        Button show = new Button("show question");
        hBox.getChildren().addAll(username, show);
        Label usernameLabel = new Label();
        usernameLabel.setTranslateX(-50);
        vbox.getChildren().addAll(hBox, usernameLabel);

        main.getChildren().add(vbox);
        pane.getChildren().add(main);

        Text question = new Text();
        TextField answer = new TextField();
        answer.setPromptText("answer");

        show.setOnMouseClicked(mouseEvent -> {
            if(username.getText().equals("")) usernameLabel.setText("please provide a username!");

            else if(User.getUserByUsername(username.getText()) != null){
                String questionNumber = User.getUserByUsername(username.getText()).getQuestionNumber();
                question.setText(SecurityQuestion.getQuestionByNumber(questionNumber));
                main.getChildren().addAll(question, answer);
            }
            else usernameLabel.setText("This username does not exist!");
        });
    }

    public void submit() throws Exception{
        LoginMenuMessages message = LoginMenuController.login(username.getText(), password.getText(), stayLoggedIn.isSelected());
        if(message.equals(LoginMenuMessages.USERNAME_DOESNT_EXIST)){
            usernameText.setText("username does not exist");
            passwordText.setText("");
        }
        else if(message.equals(LoginMenuMessages.WRONG_PASSWORD)){
            usernameText.setText("");
            passwordText.setText("wrong password");
        }

        else new MainMenuGFX().start(stage);
    }
}
