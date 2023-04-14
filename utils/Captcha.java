package main.java.model.utils;

public class Captcha {
    private  String answer;

    public Captcha() {
        answer=RandomGenerator.randomCaptchaCode();
    }
    public boolean checkAnswer(String answer){
        return this.answer.equals(answer);
    }
    public String getImage(){
        return null;
    }
}
