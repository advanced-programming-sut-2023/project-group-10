package model;

import java.util.HashMap;

public enum SecurityQuestion {
    FIRST("1", "1. What's my father's name? "),
    SECOND("2", "2. What was my first pet’s name? "),
    THIRD("3", "3. What is my mother’s last name? ");
    public final String questionNumber;
    public String question;
    private static final HashMap<String, SecurityQuestion> BY_NUMBER = new HashMap<>();

    static {
        for (SecurityQuestion question1 : values()) {
            BY_NUMBER.put(question1.questionNumber, question1);
        }
    }


    SecurityQuestion(String questionNumber, String question) {
        this.questionNumber = questionNumber;
        this.question = question;
    }

    public static String getQuestionByNumber(String questionNumber) {
        return BY_NUMBER.get(questionNumber).question;
    }

}
