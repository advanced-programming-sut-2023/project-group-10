package org.example.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    public static HashMap<String, String> separateInput(String input) {
        Pattern pattern = Pattern.compile("\"[^\"]+\"|\\S+");
        Matcher matcher = pattern.matcher(input);
        HashMap<String, String> optArg = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();

        while (matcher.find()) {
            arrayList.add(matcher.group());
        }

        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).startsWith("-") && (i == arrayList.size()-1 || arrayList.get(i+1).startsWith("-"))){
                optArg.put(arrayList.get(i), null);
            }

            else if(arrayList.get(i).startsWith("-") && !arrayList.get(i+1).startsWith("-")){
                optArg.put(arrayList.get(i), arrayList.get(i+1));
            }
        }

        return optArg;
    }
}
