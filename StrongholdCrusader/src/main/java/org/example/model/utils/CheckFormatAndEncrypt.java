package org.example.model.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class CheckFormatAndEncrypt {
    public static String encryptString(String myString) {
        return Hashing.sha256()
                .hashString(myString, StandardCharsets.UTF_8)
                .toString();
    }

    public static boolean isUsernameFormatInvalid(String username) {

        return !Pattern.compile("[a-zA-Z0-9_]+").matcher(username).matches();
    }

    public static boolean isPasswordWeak(String password) {
        //TODO: you should check if the fields are w/ spaces before even checking their formats
        return (password.length() < 6) || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")
                || !password.matches(".*[0-9].*") || !password.matches(".*[^A-Za-z0-9].*");
    }

    public static boolean isEmailFormatInvalid(String email) {
        return !Pattern.compile("[a-zA-Z0-9.@]+").matcher(email).matches()
                || !Pattern.compile(".+@.+\\..+").matcher(email).matches();
    }

    public static boolean checkNicknameFormat(String nickname) {
        return true;
    }


}