package org.example.model.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    public static String[] separateInput(String input) {
        String[] args;

        //handling quotations
        ArrayList<String> argsInQuotations = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(?<content>[^\"]+)\"");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
            argsInQuotations.add(matcher.group("content"));
        input = matcher.replaceAll("\"\"");

        input = input.trim();
        input = input.replaceAll("\\s+", " ");
        args = input.split(" ");
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("\"\"")) {
                args[i] = argsInQuotations.get(0);
                argsInQuotations.remove(0);
            }
        }
        //TODO: handle double dashed options and options with dashes in the middle (--stay-logged-in)
        return args;
    }
}