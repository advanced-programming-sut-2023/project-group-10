package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.LoginMenuController;
import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.User;
import org.example.view.enums.messages.LoginMenuMessages;
import org.example.view.enums.messages.SignupMenuMessages;

public class LoginMenu extends Application {

    public TextField username;
    public PasswordField password;
    public CheckBox stayLoggedIn;
    public Text usernameText;
    public Text passwordText;
    public VBox login;
    @FXML
    private Pane pane;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = new FXMLLoader(LoginMenu.class.getResource("/view/loginMenu.fxml")).load();
        pane.setBackground(new Background(setBackground("/images/backgrounds/background1.jpeg")));
        Scene scene = new Scene(pane, 1390, 850);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.setMaximized(true);
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
            if(username.getText().equals("")) usernameLabel.setText("please provide a username!");

            else if(User.getUserByUsername(username.getText()) != null){
                pane.getChildren().removeAll(text);
                String questionNumber = User.getUserByUsername(username.getText()).getQuestionNumber();
                question.setText(SecurityQuestion.getQuestionByNumber(questionNumber));
                pane.getChildren().add(question);
                vbox.getChildren().removeAll(hBox, usernameLabel);
                vbox.getChildren().addAll(answer, answerLabel, passwordContainer, submit);
            }
            else usernameLabel.setText("This username does not exist!");
        });

        newPassword.textProperty().addListener((observable, oldValue, newValue) -> checkPassword(newValue, passwordLabel));

        submit.setOnMouseClicked(mouseEvent -> {
            if(!User.checkSecurityAnswer(username.getText(), answer.getText()))
                answerLabel.setText("wrong answer!");
            else {
                User.getUserByUsername(username.getText()).setPassword(newPassword.getText());
                pane.getChildren().remove(0);
                pane.getChildren().remove(question);
                pane.getChildren().add(login);
            }
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

    private BackgroundImage setBackground(String url){
        Image image = new Image(GameMenu.class.getResource(url).toExternalForm(), 1440 ,900, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    private void checkPassword(String password, Label label){
        SignupMenuMessages messages = SignupMenuController.checkPassword(password);

        if(messages.equals(SignupMenuMessages.SHORT_PASSWORD))
            label.setText("short password!");
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

    public void signupMenu(MouseEvent mouseEvent) throws Exception{
        new SignupMenu().start(stage);
    }
}
