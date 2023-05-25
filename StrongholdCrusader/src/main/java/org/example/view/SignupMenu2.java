package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
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
        username.setMaxWidth(300);
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
        password.setMinWidth(226);
        password.setPromptText("password");
        Label passwordLabel = new Label();
        CheckBox showPassword = new CheckBox("show password");
        Button randomPassword = new Button("random");
        PasswordField confirmation = new PasswordField();
        confirmation.setMaxWidth(300);
        confirmation.setPromptText("confirmation");
        Label confirmationLabel = new Label();

        HBox passwordContainer = new HBox(15);
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.getChildren().addAll(password, randomPassword);
        passwordVbox.getChildren().addAll(passwordContainer, passwordLabel, confirmation, confirmationLabel, showPassword);
        vBox.getChildren().add(passwordVbox);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPassword(newValue, passwordLabel);
        });

        confirmation.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(password.getText()))
                confirmationLabel.setText("passwords don't match!");
            else confirmationLabel.setText("passwords match");
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
                if(password.getText().equals(confirmation.getText()))
                    confirmationLabel.setText("passwords match");
            }
        });

        randomPassword.setOnMouseClicked(mouseEvent -> {
            password.setText(RandomGenerator.generateSecurePassword());
        });

        VBox nicknameContainer = new VBox(5);
        nicknameContainer.setAlignment(Pos.CENTER);
        TextField nickname = new TextField();
        nickname.setMaxWidth(300);
        nickname.setPromptText("nickname");
        Label nicknameLabel = new Label();
        nicknameContainer.getChildren().addAll(nickname, nicknameLabel);
        vBox.getChildren().add(nicknameContainer);

        VBox emailContainer = new VBox(5);
        emailContainer.setAlignment(Pos.CENTER);
        TextField email = new TextField();
        email.setMaxWidth(300);
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

        Button randomSlogan = new Button("random");
        ComboBox<String> defaultSlogan = new ComboBox<>();
        defaultSlogan.setMaxWidth(300);
        defaultSlogan.setPromptText("slogan");
        for(String string : RandomGenerator.getSlogans()){
            defaultSlogan.getItems().add(string);
        }
        hasSlogan.setOnAction(actionEvent -> {
            HBox sloganContainer = new HBox(15);
            sloganContainer.setAlignment(Pos.CENTER);
            sloganContainer.getChildren().addAll(slogan, randomSlogan);
            vBox.getChildren().remove(submitContainer);
            vBox.getChildren().addAll(sloganContainer, defaultSlogan);
            vBox.getChildren().add(submitContainer);
            randomSlogan.setOnMouseClicked(mouseEvent -> {
                slogan.setText(RandomGenerator.getRandomSlogan());
            });
        });

        submit.setOnMouseClicked(mouseEvent -> {
            if (username.getText().equals("")) usernamelabel.setText("provide a username!");
            if(password.getText().equals("")) passwordLabel.setText("provide a password!");

            if(nickname.getText().equals("")) nicknameLabel.setText("provide a nickname!");
            else if(CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname.getText()))
                nicknameLabel.setText("invalid nickname format!");

            if(email.getText().equals("")) emailLabel.setText("provide an email!");
            else if(User.getUserByEmail(email.getText()) != null)
                emailLabel.setText("email already exists!");

            if (SignupMenuController.createUser(username.getText(), password.getText(), confirmation.getText(),
                    nickname.getText(), email.getText()).equals(SignupMenuMessages.SHOW_QUESTIONS));
                //TODO go to show questions menu
        });

        reset.setOnMouseClicked(mouseEvent -> {
            username.clear();
            usernamelabel.setText("");
            password.clear();
            passwordLabel.setText("");
            confirmation.clear();
            confirmationLabel.setText("");
            nickname.clear();
            nicknameLabel.setText("");
            email.clear();
            emailLabel.setText("");
            slogan.clear();
            defaultSlogan.getSelectionModel().clearSelection();
        });

        Scene scene = new Scene(borderPane, 450, 650);
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
