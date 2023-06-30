package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.connection.Client;
import org.example.connection.Packet;
import org.example.model.BackgroundBuilder;
import org.example.view.enums.messages.LoginMenuMessages;

import java.util.HashMap;

public class LoginMenu extends Application {

    private static Stage stage;
    public TextField username;
    public PasswordField password;
    public CheckBox stayLoggedIn;
    public Text usernameText;
    public Text passwordText;
    public VBox login;
    public TextField input;
    public Text captchaText;
    public Text captchaNumber;
    public Rectangle box;
    @FXML
    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = new FXMLLoader(LoginMenu.class.getResource("/view/loginMenu.fxml")).load();
        pane.setBackground(new Background(BackgroundBuilder.setBackground("/images/backgrounds/background6.jpeg")));
        Scene scene = new Scene(pane, 1390, 850);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        box.setFill(new ImagePattern(new Image(LoginMenu.class.getResource("/images/backgrounds/dotted.jpeg").toExternalForm())));
        generateCaptcha();
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
        Text text = new Text("enter your username");
        text.setTranslateX(170);
        text.setTranslateY(85);
        TextField username = new TextField();
        username.setPromptText("username");
        Button show = new Button("show question");
        hBox.getChildren().addAll(username, show);
        Label usernameLabel = new Label();
        usernameLabel.setTranslateX(-50);
        vbox.getChildren().addAll(hBox, usernameLabel);

        main.getChildren().add(vbox);
        pane.getChildren().addAll(text, main);

        Text question = new Text();
        question.setTranslateX(170);
        question.setTranslateY(85);
        TextField answer = new TextField();
        answer.setPromptText("answer");
        answer.setMaxWidth(300);
        Label answerLabel = new Label();
        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("new password");
        newPassword.setMaxWidth(300);
        Label passwordLabel = new Label();
        VBox passwordContainer = new VBox(8, newPassword, passwordLabel);
        Button submit = new Button("submit");

        show.setOnMouseClicked(mouseEvent -> {
            if (username.getText().equals("")){
                usernameLabel.setText("please provide a username!");
                return;
            }

            try {
                HashMap<String, String> attribute = new HashMap<>();
                attribute.put("username", username.getText());
                Packet packet = new Packet("live check username", attribute);
                Client.getInstance().sendPacket(packet);

                if (Client.getInstance().recievePacket().getAttribute().get("message").equals("username exists!")) {
                    pane.getChildren().removeAll(text);

                    HashMap<String, String> usernameAttribute = new HashMap<>();
                    attribute.put("username", username.getText());
                    Packet getQuestion = new Packet("get security question", usernameAttribute);
                    Client.getInstance().sendPacket(getQuestion);
                    HashMap<String, String> attributes = (HashMap<String, String>) Client.getInstance().recievePacket().getAttribute().clone();
                    String isUsernameValid = attributes.get("is username valid");
                    String questionText = attributes.get("message");

                    if (isUsernameValid.equals("ture")) {
                        question.setText(questionText);
                        pane.getChildren().add(question);
                        vbox.getChildren().removeAll(hBox, usernameLabel);
                        vbox.getChildren().addAll(answer, answerLabel, passwordContainer, submit);
                    }

                } else usernameLabel.setText("This username does not exist!");
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("password", newValue);
            Packet packet = new Packet("live check password", attributes);
            try {
                Client.getInstance().sendPacket(packet);
                passwordLabel.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        submit.setOnMouseClicked(mouseEvent -> {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("username", username.getText());
            attributes.put("answer", answer.getText());
            attributes.put("new password", newPassword.getText());
            Packet packet = new Packet("try to change password", attributes);
            try {
                Client.getInstance().sendPacket(packet);
                String result = Client.getInstance().recievePacket().getAttribute().get("is successful");
                if (result.equals("false")) answerLabel.setText("wrong answer!");
                else {
                    pane.getChildren().remove(0);
                    pane.getChildren().remove(question);
                    pane.getChildren().add(login);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void generateCaptcha() {
        try {
            Packet packet = new Packet("get captcha", null);
            Client.getInstance().sendPacket(packet);
            String number = Client.getInstance().recievePacket().getAttribute().get("number");
            captchaNumber.setText(number);
            captchaNumber.setFill(Color.DARKGRAY);
            captchaNumber.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
            captchaNumber.setStrikethrough(true);
            box.setWidth(captchaNumber.getText().length() * 15);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void submit() throws Exception {
        if (!input.getText().equals(captchaNumber.getText())) {
            captchaText.setText("incorrect captcha!");
            usernameText.setText("");
            passwordText.setText("");
            generateCaptcha();
            return;
        }

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("username", username.getText());
        attributes.put("password", password.getText());
        Packet packet = new Packet("log in", attributes);
        Client.getInstance().sendPacket(packet);
        String message = Client.getInstance().recievePacket().getAttribute().get("message value");
        if (message.equals(LoginMenuMessages.USERNAME_DOESNT_EXIST.name())) {
            usernameText.setText("username does not exist");
            passwordText.setText("");
            captchaText.setText("");
        } else if (message.equals(LoginMenuMessages.WRONG_PASSWORD.name())) {
            usernameText.setText("");
            passwordText.setText("wrong password");
            captchaText.setText("");
        } //else new MainMenuGFX().start(stage);
        //TODO
    }

    public void signupMenu(MouseEvent mouseEvent) throws Exception {
        new SignupMenu().start(stage);
    }
}
