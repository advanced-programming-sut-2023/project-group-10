package org.example.model.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomGenerator {
    private static int tradeNumber = 0;
    private static final String[] slogans = {
            "I have dispatched another of our enemies, my friend. The struggle continues!",
            "You fight well, my friend. You have struck a mighty blow for our cause.",
            "I am lost, my friend. You must continue the struggle... without me.",
            "My men approach, you will trouble me no more",
            "I will tear down your castle, stone by stone if I have to! But I will have your head!",
            "Soon you will see what it means to be Real Warfare!",
            "Is there no one who will rid me of your irritating presence?!",
            "Your time on this earth is limited. Time to say your prayers!",
            "I will kill you soon! You and all your vermin!"
    };

    public static String[] getSlogans(){
        return slogans;
    }

    public static String getRandomSlogan() {
        Random random = new Random();
        return slogans[random.nextInt(slogans.length)];
    }

    public static String generateSecurePassword() {
        Random randomNumberOfLetters=new Random();
        String upperCaseStr = RandomStringUtils.random(randomNumberOfLetters.nextInt(4)+2, 65, 90, true, true);
        String lowerCaseStr = RandomStringUtils.random(randomNumberOfLetters.nextInt(4)+2, 97, 122, true, true);
        String numbersStr = RandomStringUtils.randomNumeric(2);
        String specialCharStr = RandomStringUtils.random(2, 33, 47, false, false);
        String totalCharsStr = RandomStringUtils.randomAlphanumeric(2);
        String demoPassword = upperCaseStr.concat(lowerCaseStr)
                .concat(numbersStr)
                .concat(specialCharStr)
                .concat(totalCharsStr);
        List<Character> listOfChar = demoPassword.chars()
                .mapToObj(data -> (char) data)
                .collect(Collectors.toList());
        Collections.shuffle(listOfChar);
        return listOfChar.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String tradeId() {
        tradeNumber++;
        return Integer.toString(tradeNumber);
    }
}