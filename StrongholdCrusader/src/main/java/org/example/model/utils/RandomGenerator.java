package org.example.model.utils;

import java.util.Random;

public class RandomGenerator {
    private static final String[] slogans={
            "I have dispatched another of our enemies, my friend. The struggle continues!",
            "You fight well, my friend. You have struck a mighty blow for our cause.",
            "I am lost, my friend. You must continue the struggle... without me.",
            "My men approach, you will trouble me no more",
            "I will tear down your castle, stone by stone if i have to! But i will have your head!",
            "Soon you will see what it means to be Real Warfare!",
            "Is there no one who will rid me of your irritating presence?!",
            "Your time on this earth is limited. Time to say your prayers!",
            "I will kill you soon! You and all your vermin!"
    };
    public String getRandomPassword(){

        return null;
    }
    public  static String getRandomSlogan(){
        Random random=new Random(slogans.length);
        return slogans[random.nextInt()-1];
    }
    public static String randomCaptchaCode(){
        return null;
    }
}