package org.example.connection.Handlers;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CaptchaGenerator;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;
import java.util.HashMap;

public class SignupMenuHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public SignupMenuHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void initializeApp() throws IOException {
        Stronghold.initializeApp();
        Packet toBeSent = new Packet(ServerToClientCommands.INITIALIZE_COMPLETE.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void getDefaultSlogans() throws IOException {
        String allSlogans = "";
        for (String slogan : RandomGenerator.getSlogans())
            allSlogans = allSlogans.concat(slogan + "\n");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("slogans", allSlogans);
        Packet toBeSent = new Packet(ServerToClientCommands.DEFAULT_SLOGANS.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void checkUsername() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        SignupMenuMessages message = SignupMenuController.checkUsername(username);
        String messageValue;
        if (message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT)) messageValue = "invalid username format!";
        else if (message.equals(SignupMenuMessages.USER_EXISTS)) messageValue = "username exists!";
        else messageValue = "valid username!";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.USERNAME_CHECK.getCommand(), hashMap);
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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.PASSWORD_CHECK.getCommand(), hashMap);
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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.EMAIL_CHECK.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void checkNickname() throws IOException {
        String nickname = receivedPacket.getAttribute().get("nickname");
        String messageValue;
        if(CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname))
            messageValue = "invalid nickname format!";
        else messageValue = ""; //valid nickname
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.NICKNAME_CHECK.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void generateRandomPassword() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("password", RandomGenerator.generateSecurePassword());
        Packet toBeSent = new Packet(ServerToClientCommands.RANDOM_PASSWORD.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void generateRandomSlogan() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("slogan", RandomGenerator.getRandomSlogan());
        Packet toBeSent = new Packet(ServerToClientCommands.RANDOM_SLOGAN.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void generateCaptcha() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("number", CaptchaGenerator.randomNumberGenerator());
        Packet toBeSent = new Packet(ServerToClientCommands.GET_CAPTCHA.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void checkSignUpInfo() throws IOException{
        String username = receivedPacket.getAttribute().get("username");
        String password = receivedPacket.getAttribute().get("password");
        String passwordConfirmation = receivedPacket.getAttribute().get("password confirmation");
        String nickname = receivedPacket.getAttribute().get("nickname");
        String email = receivedPacket.getAttribute().get("email");
        SignupMenuController.createUser(username, password, passwordConfirmation, email, nickname).name();
        Packet toBeSent = new Packet(ServerToClientCommands.CAN_GO_TO_SECURITY_QUESTIONS.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void getSecurityQuestions() throws IOException {
        // format: [q number] [q body]\n
        String allQuestions = SecurityQuestion.getAllQuestionsString();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", allQuestions);
        Packet toBeSent = new Packet(ServerToClientCommands.SECURITY_QUESTIONS.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void completeSignup() throws IOException{
        String username = receivedPacket.getAttribute().get("username");
        String password = receivedPacket.getAttribute().get("password");
        String nickname = receivedPacket.getAttribute().get("nickname");
        String email = receivedPacket.getAttribute().get("email");
        String slogan = receivedPacket.getAttribute().get("slogan");
        String questionNumber = receivedPacket.getAttribute().get("question number");
        String answer = receivedPacket.getAttribute().get("answer");
        SignupMenuController.createUser(questionNumber, answer, username, password, nickname, slogan, email);
        Packet toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_SIGNUP.getCommand(), null);
        connection.sendPacket(toBeSent);
    }

    public void getLoggedInUser() throws IOException{
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user object", userObjectToJson(Stronghold.getLoggedInUserFromFile()));
        Packet toBeSent = new Packet(ServerToClientCommands.SEND_LOGGED_IN_USER.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    private String userObjectToJson(User user) {
        // null object serialized as empty string
        if (user == null) return "";
        return new Gson().toJson(user, User.class);
    }
}
