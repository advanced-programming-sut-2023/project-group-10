package org.example.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.SignupMenuMessages;

public class SignupMenu2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new FXMLLoader(SignupMenu2.class.getResource("/view/signupMenu.fxml")).load();

        VBox vBox = new VBox(25);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);

        VBox usernameContainer = new VBox(5);
        usernameContainer.setAlignment(Pos.CENTER);
        TextField username = new TextField();
        username.setMaxWidth(240);
        username.setPromptText("username");
        Label usernamelabel = new Label();
        usernameContainer.getChildren().addAll(username, usernamelabel);
        vBox.getChildren().add(usernameContainer);

        username.textProperty().addListener((observable, olaValue, newValue) -> {
            checkUsername(newValue, usernamelabel);
        });

        VBox passwordVbox = new VBox(8);
        passwordVbox.setAlignment(Pos.CENTER);
        PasswordField password = new PasswordField();
        password.setMinWidth(166);
        password.setPromptText("password");
        Label passwordLabel = new Label();
        CheckBox showPassword = new CheckBox("show password");
        Button randomPassword = new Button("random");

        HBox passwordContainer = new HBox(15);
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.getChildren().addAll(password, randomPassword);
        passwordVbox.getChildren().addAll(passwordContainer, passwordLabel, showPassword);
        vBox.getChildren().add(passwordVbox);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPassword(newValue, passwordLabel);
        });

        showPassword.setOnAction(actionEvent -> {
            if(!password.getText().equals("")){
                String passwordText = password.getText();
                String label = passwordLabel.getText();
                password.clear();
                passwordLabel.setText(label);
                password.setPromptText(passwordText);
            }
            else{
                String passwordText = password.getPromptText();
                password.setPromptText("password");
                password.setText(passwordText);
            }
        });

        VBox nicknameContainer = new VBox(5);
        nicknameContainer.setAlignment(Pos.CENTER);
        TextField nickname = new TextField();
        nickname.setMaxWidth(240);
        nickname.setPromptText("nickname");
        Label nicknameLabel = new Label();
        nicknameContainer.getChildren().addAll(nickname, nicknameLabel);
        vBox.getChildren().add(nicknameContainer);

        VBox emailContainer = new VBox(5);
        emailContainer.setAlignment(Pos.CENTER);
        TextField email = new TextField();
        email.setMaxWidth(240);
        email.setPromptText("email");
        Label emailLabel = new Label();
        emailContainer.getChildren().addAll(email, emailLabel);
        vBox.getChildren().add(emailContainer);

        CheckBox hasSlogan = new CheckBox("create slogan");
        vBox.getChildren().add(hasSlogan);

        Button submit = new Button("submit");
        Button reset = new Button("reset");
        HBox submitContainer = new HBox(10, submit, reset);
        submitContainer.setAlignment(Pos.CENTER);
        vBox.getChildren().add(submitContainer);

        TextField slogan = new TextField();
        slogan.setMaxWidth(300);
        slogan.setPromptText("slogan");

        Button button = new Button("random");
        ComboBox<String> defaultSlogan = new ComboBox<>();
        defaultSlogan.setMaxWidth(240);
        defaultSlogan.setPromptText("slogan");
        for(String string : RandomGenerator.getSlogans()){
            defaultSlogan.getItems().add(string);
        }
        hasSlogan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HBox sloganContainer = new HBox(15);
                sloganContainer.setAlignment(Pos.CENTER);
                sloganContainer.getChildren().addAll(slogan, button);
                vBox.getChildren().remove(submitContainer);
                vBox.getChildren().addAll(sloganContainer, defaultSlogan);
                vBox.getChildren().add(submitContainer);
            }
        });

        Scene scene = new Scene(borderPane, 400, 600);
        stage.setScene(scene);
        stage.setTitle("signup menu");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(SignupMenu2.class, args);
    }

    private void checkUsername(String username, Label label){
        SignupMenuMessages message = SignupMenuController.checkUsername(username);

        if(message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT))
            label.setText("invalid username format!");
        else if(message.equals(SignupMenuMessages.USER_EXISTS))
            label.setText("username exists!");
        else label.setText("valid username!");
    }

    private void checkPassword(String password, Label label){
        SignupMenuMessages messages = SignupMenuController.checkPassword(password);

        if(messages.equals(SignupMenuMessages.SHORT_PASSWORD))
            label.setText("short Password!");
        else if(messages.equals(SignupMenuMessages.NO_LOWERCASE_LETTER))
            label.setText("password must have a lowercase letter");
        else if(messages.equals(SignupMenuMessages.NO_UPPERCASE_LETTER))
            label.setText("password must have an uppercase letter");
        else if(messages.equals(SignupMenuMessages.NO_NUMBER))
            label.setText("password must have a digit");
        else if(messages.equals(SignupMenuMessages.NO_SPECIAL_CHARACTER))
            label.setText("password must have a special character");
        else label.setText("valid password!");
    }
}
