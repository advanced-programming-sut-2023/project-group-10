package org.example.model.utils;

import org.example.model.Stronghold;
import org.example.model.game.envirnmont.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    public static HashMap<String, String> separateInput(String input) {
        Pattern pattern = Pattern.compile("\"[^\"]+\"|\\S+");
        Matcher matcher = pattern.matcher(input);
        HashMap<String, String> optArg = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();

        String temp;
        while (matcher.find()) {
            temp = matcher.group();
            if (temp.matches("^\".*\"$"))
                temp = temp.replaceAll("^\"", "").replaceAll("\"$", "");
            arrayList.add(temp);
        }

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).startsWith("-") && (i == arrayList.size() - 1 || arrayList.get(i + 1).startsWith("-")))
                optArg.put(arrayList.get(i), null);

            else if (arrayList.get(i).startsWith("-") && !arrayList.get(i + 1).startsWith("-"))
                optArg.put(arrayList.get(i), arrayList.get(i + 1));
        }

        return optArg;
    }

    public static Coordinate getCoordinateFromXYInput(String input, String flag1, String flag2) throws Exception {
        return getCoordinateFromXYInput(input, flag1, flag2, Stronghold.getCurrentBattle().getBattleMap().getSize());
    }

    public static Coordinate getCoordinateFromXYInput(String input, String flag1, String flag2, int mapSize) throws Exception {
        return getCoordinateFromXYInput(InputProcessor.separateInput(input), flag1, flag2, mapSize);
    }

    public static Coordinate getCoordinateFromXYInput(HashMap<String, String> options, String flag1, String flag2) throws Exception {
        return getCoordinateFromXYInput(options, flag1, flag2, Stronghold.getCurrentBattle().getBattleMap().getSize());
    }

    public static Coordinate getCoordinateFromXYInput(HashMap<String, String> options, String flag1, String flag2, int mapSize) throws Exception {
        String x = "";
        String y = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals(flag1)) x = option.getValue();
            else if (option.getKey().equals(flag2)) y = option.getValue();
            else throw new Exception("invalid option");
        }

        flag1 = flag1.replaceAll("^-", "");
        flag2 = flag2.replaceAll("^-", "");
        if (x == null || y == null) throw new Exception("empty field");
        if (x.isEmpty() || y.isEmpty()) throw new Exception("missing argument");
        if (!x.matches("-?\\d+")) throw new Exception("invalid " + flag1);
        if (!y.matches("-?\\d+")) throw new Exception("invalid " + flag2);
        int xInt = Integer.parseInt(x);
        int yInt = Integer.parseInt(y);
        if (xInt < 0 || xInt >= mapSize) throw new Exception(flag1 + " out of bounds");
        if (yInt < 0 || yInt >= mapSize) throw new Exception(flag2 + " out of bounds");
        return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
    }

    public static Integer rateInputProcessor(String input) throws Exception {
        String rate = "";
        Pattern pattern = Pattern.compile(".*-r\\s*(?<rate>-?\\S+).*");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            rate = matcher.group("rate");
            input = input.replaceFirst("-r", "");
            input = input.replaceFirst(rate, "");
        }
        HashMap<String, String> options = InputProcessor.separateInput(input);
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-r")) {
                throw new Exception("more than one -r");
            } else throw new Exception("invalid option");
        }
        if (!rate.matches("-?\\d+")) throw new Exception("You should enter a number for rate!");
        return Integer.parseInt(rate);
    }
}
