package org.example.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    public static HashMap<String, String> separateInput(String input) {
        Pattern pattern = Pattern.compile("(-(?<option>.)\\s+(?<argument>\".*\"|[^\\s\\-]*))");
        Matcher matcher = pattern.matcher(input);
        HashMap<String, String> optArg = new HashMap<>();

        while (matcher.find()) {
            optArg.put(matcher.group("option"), matcher.group("argument"));
        }

        return optArg;
    }
}
