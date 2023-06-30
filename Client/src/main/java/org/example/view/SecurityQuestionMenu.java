package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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

import java.util.HashMap;

public class SecurityQuestionMenu extends Application {

    private static Stage stage;
    public RadioButton question1;
    public RadioButton question2;
    public RadioButton question3;
    public TextField answer;
    public Button submit;
    public Rectangle box;
    public Text captchaNumber;
    public TextField input;
    public Text captchaText;
    public VBox captchaContainer;
    private String question;

    @Override
    public void start(Stage stage) throws Exception {
        SecurityQuestionMenu.stage = stage;
        BorderPane borderPane = new FXMLLoader(SecurityQuestionMenu.class.getResource("/view/securityQuestion.fxml")).load();
        Background background = new Background(BackgroundBuilder.setBackground("/images/backgrounds/background7.jpeg"));
        borderPane.setBackground(background);

        Scene scene = new Scene(borderPane, 1390, 850);
        stage.setScene(scene);
        stage.setTitle("Security Question");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        Packet packet = new Packet("get security questions", null);
        try {
            Client.getInstance().sendPacket(packet);
            String slogans = Client.getInstance().recievePacket().getAttribute().get("message");
            String[] sloganArray = slogans.split("\n");
            question1.setText(sloganArray[0]);
            question2.setText(sloganArray[1]);
            question3.setText(sloganArray[2]);
        } catch (Exception e){
            e.printStackTrace();
        }

        box.setFill(new ImagePattern(new Image(LoginMenu.class.getResource("/images/backgrounds/dotted.jpeg").toExternalForm())));
        generateCaptcha();
    }

    public void question1() {
        answer.setVisible(true);
        submit.setVisible(true);
        captchaContainer.setVisible(true);
        question = "1";
    }

    public void question2() {
        answer.setVisible(true);
        submit.setVisible(true);
        captchaContainer.setVisible(true);
        question = "2";
    }

    public void question3() {
        answer.setVisible(true);
        submit.setVisible(true);
        captchaContainer.setVisible(true);
        question = "3";
    }

    public void submit() throws Exception {
        if (!input.getText().equals(captchaNumber.getText())) {
            captchaText.setText("incorrect captcha!");
            generateCaptcha();
            return;
        }

        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("username", DataBank.getUsername());
        attributes.put("password", DataBank.getPassword());
        attributes.put("nickname", DataBank.getNickname());
        attributes.put("email", DataBank.getEmail());
        attributes.put("slogan", DataBank.getSlogan());
        attributes.put("question number", question);
        attributes.put("answer", answer.getText());
        Packet packet = new Packet("complete signup", attributes);
        Client.getInstance().sendPacket(packet);
        new LoginMenu().start(stage);
    }

    public void generateCaptcha() {
        Packet packet = new Packet("get captcha", null);
        try {
            Client.getInstance().sendPacket(packet);
            String captcha = Client.getInstance().recievePacket().getAttribute().get("number");
            captchaNumber.setText(captcha);
            captchaNumber.setFill(Color.DARKGRAY);
            captchaNumber.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
            captchaNumber.setStrikethrough(true);
            box.setWidth(captchaNumber.getText().length() * 15);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
