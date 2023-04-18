package org.example.model;

import java.util.HashMap;

public class SecurityQuestion {
    private static final HashMap<String, String> securityQuestions = new HashMap<>();

    static {
        securityQuestions.put("1", "1. What's my father's name? ");
        securityQuestions.put("2", "2. What was my first pet’s name? ");
        securityQuestions.put("3", "3. What is my mother’s last name? ");
    }

    public static String getQuestionByNumber(String questionNumber) {
        return securityQuestions.get(questionNumber);
    }

}
