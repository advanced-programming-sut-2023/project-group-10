package org.example.model.utils;

import org.example.controller.CustomizeMapController;
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

        while (matcher.find()) {
            arrayList.add(matcher.group());
        }

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).startsWith("-") && (i == arrayList.size() - 1 || arrayList.get(i + 1).startsWith("-")))
                optArg.put(arrayList.get(i), null);

            else if (arrayList.get(i).startsWith("-") && !arrayList.get(i + 1).startsWith("-"))
                optArg.put(arrayList.get(i), arrayList.get(i + 1));
        }

        return optArg;
    }

    //TODO: change location if necessary
    public static Coordinate getCoordinateFromXYInput(String input, String flag1, String flag2) throws Exception {
        return getCoordinateFromXYInput(InputProcessor.separateInput(input), flag1, flag2);
    }

    public static Coordinate getCoordinateFromXYInput(HashMap<String, String> options, String flag1, String flag2) throws Exception {
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
        org.example.model.game.envirnmont.Map map = Stronghold.getCurrentBattle().getBattleMap();
        if (!CustomizeMapController.isIndexInBounds(Integer.parseInt(x))) throw new Exception(flag1 + " out of bounds");
        if (!CustomizeMapController.isIndexInBounds(Integer.parseInt(y))) throw new Exception(flag2 + " out of bounds");
        return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
    }

    public static Integer rateInputProcessor(String input) throws Exception {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String rate = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-r")) {
                rate = option.getValue();
            } else throw new Exception("invalid option");
        }
        if (!rate.matches("-?\\d+")) throw new Exception("You should enter a number for rate!");
        return Integer.parseInt(rate);
    }
}
