package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.connection.Client;
import org.example.connection.Packet;
import org.example.model.BackgroundBuilder;
import org.example.view.enums.messages.SignupMenuMessages;

import java.util.HashMap;

public class SignupMenu extends Application {
    public static Stage stage;
    public TextField username;
    public Label usernameLabel;
    public PasswordField password;
    public Button randomPassword;
    public Label passwordLabel;
    public PasswordField confirmation;
    public Label confirmationLabel;
    public CheckBox showPassword;
    public TextField nickname;
    public Label nicknameLabel;
    public TextField email;
    public Label emailLabel;
    public CheckBox hasSlogan;
    public VBox main;
    public Button submit;
    public Button reset;
    public Text loginMenu;
    public TextField slogan;
    public Button randomSlogan;
    public ComboBox defaultSlogan;
    public HBox submitContainer;
    public HBox sloganContainer;

    public static void main(String[] args) {
        //Stronghold.initializeApp();
        //TODO send packet initializeApp
        Application.launch(SignupMenu.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SignupMenu.stage = stage;

        //Stronghold.initializeApp();
        //TODO send packet initializeApp
        if (Stronghold.getLoggedInUserFromFile() != null) {
            //Stronghold.setCurrentUser(Stronghold.getLoggedInUserFromFile());
            //TODO get loggedInUser
            new MainMenuGFX().start(stage);
        } else {
            BorderPane borderPane = new FXMLLoader(SignupMenu.class.getResource("/view/signupMenu.fxml")).load();
            Background background = new Background(BackgroundBuilder.setBackground("/images/backgrounds/background2.png"));
            borderPane.setBackground(background);

            Scene scene = new Scene(borderPane, 1390, 850);
            stage.setScene(scene);
            stage.setTitle("signup menu");
            stage.setMaximized(true);
            stage.show();
        }
    }

    @FXML
    public void initialize() {
        Packet packet = new Packet("get default slogans", null);
        for (String string : RandomGenerator.getSlogans()) {
            //TODO getRandomSlogan
            defaultSlogan.getItems().add(string);
        }

        username.textProperty().addListener((observable, olaValue, newValue) -> {
            checkUsername(newValue, usernameLabel);
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPassword(newValue, passwordLabel);
        });

        confirmation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(password.getText()))
                confirmationLabel.setText("passwords don't match!");
            else confirmationLabel.setText("passwords match");
        });

        showPassword.setOnAction(actionEvent -> setShowPassword());

        randomPassword.setOnMouseClicked(mouseEvent -> {
            //TODO get secure password
            Packet randomPassword = new Packet("random password", null);
            password.setText(RandomGenerator.generateSecurePassword());
        });

        submit.setOnMouseClicked(MouseEvent -> submitUser());

        reset.setOnMouseClicked(mouseEvent -> {
            try {
                resetInputs();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        loginMenu.setOnMouseClicked(mouseEvent -> {
            try {
                new LoginMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        hasSlogan.setOnAction(actionEvent -> {
            main.getChildren().removeAll(submitContainer, loginMenu);
            sloganContainer.setVisible(true);
            defaultSlogan.setVisible(true);
            main.getChildren().addAll(submitContainer, loginMenu);
            randomSlogan.setOnMouseClicked(mouseEvent -> {
                //TODO get random slogan
                slogan.setText(RandomGenerator.getRandomSlogan());
            });
        });
    }

    private void submitUser() {
        if (username.getText().equals("")) usernameLabel.setText("provide a username!");
        if (password.getText().equals("")) passwordLabel.setText("provide a password!");
        if (nickname.getText().equals("")) nicknameLabel.setText("provide a nickname!");
        else if (CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname.getText()))
            //TODO is nickname valid?
            nicknameLabel.setText("invalid nickname format!");

        if (email.getText().equals("")) emailLabel.setText("provide an email!");
        else if (CheckFormatAndEncrypt.isEmailFormatInvalid(email.getText()))
            //TODO is email valid
            emailLabel.setText("invalid email format!");
        else if (User.getUserByEmail(email.getText()) != null)
            emailLabel.setText("email already exists!");

        else {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("username", username.getText());
            attributes.put("password", password.getText());
            attributes.put("password confirmation", confirmation.getText());
            attributes.put("nickname", nickname.getText());
            attributes.put("email", email.getText());
            Packet packet = new Packet("check sign up info", attributes);
            try {
                DataBank.setUsername(username.getText());
                DataBank.setPassword(password.getText());
                DataBank.setNickname(nickname.getText());
                DataBank.setEmail(email.getText());
                DataBank.setSlogan(slogan.getText());
                goToSecurityMenu();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void resetInputs() throws Exception {
        username.clear();
        usernameLabel.setText("");
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
        goToSecurityMenu();
    }

    private void setShowPassword() {
        if (!password.getText().equals("")) {
            String passwordText = password.getText();
            String label = passwordLabel.getText();
            password.clear();
            passwordLabel.setText(label);
            password.setPromptText(passwordText);
        } else {
            String passwordText = password.getPromptText();
            password.setPromptText("password");
            password.setText(passwordText);
            if (password.getText().equals(confirmation.getText()))
                confirmationLabel.setText("passwords match");
        }
    }

    private void checkUsername(String username, Label label) {
        //TODO check username
        SignupMenuMessages message = SignupMenuController.checkUsername(username);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("username", username);
        Packet packet = new Packet("live check username", attributes);

        if (message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT))
            label.setText("invalid username format!");
        else if (message.equals(SignupMenuMessages.USER_EXISTS))
            label.setText("username exists!");
        else label.setText("valid username!");
    }

    private void checkPassword(String password, Label label) {
        //TODO check password
        SignupMenuMessages messages = SignupMenuController.checkPassword(password);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("password", password);
        Packet packet = new Packet("live check password", attributes);

        if (messages.equals(SignupMenuMessages.SHORT_PASSWORD))
            label.setText("short password!");
        else if (messages.equals(SignupMenuMessages.NO_LOWERCASE_LETTER))
            label.setText("password must have a lowercase letter");
        else if (messages.equals(SignupMenuMessages.NO_UPPERCASE_LETTER))
            label.setText("password must have an uppercase letter");
        else if (messages.equals(SignupMenuMessages.NO_NUMBER))
            label.setText("password must have a digit");
        else if (messages.equals(SignupMenuMessages.NO_SPECIAL_CHARACTER))
            label.setText("password must have a special character");
        else label.setText("valid password!");
    }

    private void goToSecurityMenu() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Signup Successful!");
        alert.setContentText("You will now enter \"security question\" menu!");
        alert.showAndWait();
        new SecurityQuestionMenu().start(stage);
    }
}