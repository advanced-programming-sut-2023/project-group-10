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
import java.util.random.RandomGenerator;

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
    public ComboBox<String> defaultSlogan;
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
        try {
            Client.getInstance().sendPacket(packet);
            String gson = Client.getInstance().recievePacket().getAttribute().get("slogans");
            for (String string : gson.split("\n")) {
                defaultSlogan.getItems().add(string);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        username.textProperty().addListener((observable, olaValue, newValue) -> {
            HashMap<String, String> attribute = new HashMap<>();
            attribute.put("username", newValue);
            Packet username = new Packet("live check username", attribute);
            try {
                Client.getInstance().sendPacket(username);
                usernameLabel.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("password", newValue);
            Packet password = new Packet("live check password", attributes);
            try {
                Client.getInstance().sendPacket(password);
                passwordLabel.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        confirmation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(password.getText()))
                confirmationLabel.setText("passwords don't match!");
            else confirmationLabel.setText("passwords match");
        });

        showPassword.setOnAction(actionEvent -> setShowPassword());

        randomPassword.setOnMouseClicked(mouseEvent -> {
            Packet randomPassword = new Packet("random password", null);
            try {
                Client.getInstance().sendPacket(randomPassword);
                password.setText(Client.getInstance().recievePacket().getAttribute().get("password"));
            } catch (Exception e){
                e.printStackTrace();
            }
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

    private void submitUser() throws Exception {
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
            Client.getInstance().sendPacket(packet);
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

    private void goToSecurityMenu() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Signup Successful!");
        alert.setContentText("You will now enter \"security question\" menu!");
        alert.showAndWait();
        new SecurityQuestionMenu().start(stage);
    }
}