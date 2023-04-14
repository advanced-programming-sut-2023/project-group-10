package model.utils;

public class CaptchaGenerator {
    private final String answer;

    public CaptchaGenerator() {
        answer = RandomGenerator.randomCaptchaCode();
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }

    public String getCaptcha() {
        return null;
    }
}
