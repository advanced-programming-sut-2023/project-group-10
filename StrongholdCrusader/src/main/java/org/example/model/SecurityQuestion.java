package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class SecurityQuestion {
    public static final HashMap<String, String> securityQuestions = new HashMap<>();

    static {
        securityQuestions.put("1", "What's my father's name?");
        securityQuestions.put("2", "What was my first pet’s name?");
        securityQuestions.put("3", "What is my mother’s last name?");
    }

    public static String getAllQuestionsString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> question : securityQuestions.entrySet())
            result.append(question.getKey()).append(".").append(" ").append(question.getValue()).append("\n");
        return result.toString();
    }

    public static String getQuestionByNumber(String questionNumber) {
        return securityQuestions.get(questionNumber);
    }

}
