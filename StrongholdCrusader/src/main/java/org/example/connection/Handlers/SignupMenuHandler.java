package org.example.connection.Handlers;

import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.User;
import org.example.model.utils.CaptchaGenerator;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupMenuHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public SignupMenuHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void getDefaultSlogans() throws IOException {
        String allSlogans = "";
        for (String slogan : RandomGenerator.getSlogans())
            allSlogans = allSlogans.concat(slogan + "\n");
        Packet toBeSent = new Packet(ServerToClientCommands.DEFAULT_SLOGANS.getCommand(), (HashMap<String, String>) Map.of("slogans", allSlogans));
        connection.sendPacket(toBeSent);
    }

    public void checkUsername() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        SignupMenuMessages message = SignupMenuController.checkUsername(username);
        String messageValue;
        if (message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT)) messageValue = "invalid username format!";
        else if (message.equals(SignupMenuMessages.USER_EXISTS)) messageValue = "username exists!";
        else messageValue = "valid username!";
        Packet toBeSent = new Packet(ServerToClientCommands.USERNAME_CHECK.getCommand(), (HashMap<String, String>) Map.of("message", messageValue));
        connection.sendPacket(toBeSent);
    }

    public void checkPassword() throws IOException {
        String password = receivedPacket.getAttribute().get("password");
        SignupMenuMessages message = SignupMenuController.checkPassword(password);
        String messageValue;
        if (message == SignupMenuMessages.SHORT_PASSWORD) messageValue = "short password!";
        else if (message == SignupMenuMessages.NO_LOWERCASE_LETTER)
            messageValue = "password must have a lowercase letter";
        else if (message == SignupMenuMessages.NO_UPPERCASE_LETTER)
            messageValue = "password must have an uppercase letter";
        else if (message == SignupMenuMessages.NO_NUMBER) messageValue = "password must have a digit";
        else if (message == SignupMenuMessages.NO_SPECIAL_CHARACTER)
            messageValue = "password must have a special character";
        else messageValue = "valid password!";
        Packet toBeSent = new Packet(ServerToClientCommands.PASSWORD_CHECK.getCommand(), (HashMap<String, String>) Map.of("message", messageValue));
        connection.sendPacket(toBeSent);
    }

    public void checkPasswordConfirmation() throws IOException {
        String password = receivedPacket.getAttribute().get("password");
        String passwordConfirmation = receivedPacket.getAttribute().get("password confirmation");
        String messageValue;
        if (password.equals(passwordConfirmation)) messageValue = "passwords match";
        else messageValue = "passwords don't match!";
        Packet toBeSent = new Packet(ServerToClientCommands.PASSWORD_CONFIRMATION_CHECK.getCommand(), (HashMap<String, String>) Map.of("message", messageValue));
        connection.sendPacket(toBeSent);
    }

    public void checkEmail() throws IOException {
        String email = receivedPacket.getAttribute().get("email");
        String messageValue;
        if (CheckFormatAndEncrypt.isEmailFormatInvalid(email))
            messageValue = "invalid email format!";
        else if (User.getUserByEmail(email) != null)
            messageValue = "email already exists!";
        else messageValue = ""; //valid email
        Packet toBeSent = new Packet(ServerToClientCommands.EMAIL_CHECK.getCommand(), (HashMap<String, String>) Map.of("message", messageValue));
        connection.sendPacket(toBeSent);
    }

    public void checkNickname() throws IOException {
        String nickname = receivedPacket.getAttribute().get("nickname");
        String messageValue;
        if(CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname))
            messageValue = "invalid nickname format!";
        else messageValue = ""; //valid nickname
        Packet toBeSent = new Packet(ServerToClientCommands.NICKNAME_CHECK.getCommand(), (HashMap<String, String>) Map.of("message", messageValue));
        connection.sendPacket(toBeSent);
    }

    public void generateRandomPassword() throws IOException {
        Packet toBeSent = new Packet(ServerToClientCommands.RANDOM_PASSWORD.getCommand(), (HashMap<String, String>) Map.of("password", RandomGenerator.generateSecurePassword()));
        connection.sendPacket(toBeSent);
    }

    public void generateRandomSlogan() throws IOException {
        Packet toBeSent = new Packet(ServerToClientCommands.RANDOM_SLOGAN.getCommand(), (HashMap<String, String>) Map.of("slogan", RandomGenerator.getRandomSlogan()));
        connection.sendPacket(toBeSent);
    }

    public void generateCaptcha() throws IOException {
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CAPTCHA.getCommand(), (HashMap<String, String>) Map.of("number", CaptchaGenerator.randomNumberGenerator()));
        connection.sendPacket(toBeSent);
    }

    public void checkSignUpInfo() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        String password = receivedPacket.getAttribute().get("password");
        String passwordConfirmation = receivedPacket.getAttribute().get("password confirmation");
        String nickname = receivedPacket.getAttribute().get("nickname");
        String email = receivedPacket.getAttribute().get("email");
        SignupMenuMessages result = SignupMenuController.createUser(username, password, passwordConfirmation, email, nickname);
        boolean stateValue;
        stateValue = result == SignupMenuMessages.SHOW_QUESTIONS;
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_GO_TO_SECURITY_QUESTIONS.getCommand(), (HashMap<String, String>) Map.of("state", String.valueOf(stateValue)));
        connection.sendPacket(toBeSent);
    }

    public void getSecurityQuestions() throws IOException {
        // format: [q number] [q body]\n
        String allQuestions = SecurityQuestion.getAllQuestionsString();
        Packet toBeSent = new Packet(ServerToClientCommands.SECURITY_QUESTIONS.getCommand(), (HashMap<String, String>) Map.of("message", allQuestions));
        connection.sendPacket(toBeSent);
    }

    public void completeSignup() {
        String username = receivedPacket.getAttribute().get("username");
        String password = receivedPacket.getAttribute().get("password");
        String nickname = receivedPacket.getAttribute().get("nickname");
        String email = receivedPacket.getAttribute().get("email");
        String slogan = receivedPacket.getAttribute().get("slogan");
        String questionNumber = receivedPacket.getAttribute().get("question number");
        String answer = receivedPacket.getAttribute().get("answer");

        SignupMenuController.createUser(questionNumber, answer, username, password, nickname, slogan, email);
        Packet toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_SIGNUP.getCommand(), null);
    }
}
