package org.example.model.utils;

import org.apache.commons.cli.*;

public class ExceptionMessages {
    public static String getMessageFromException(ParseException exception) {
        if (exception instanceof AlreadySelectedException)
            return "error: " + ((AlreadySelectedException) exception).getOption().getDescription() + " was entered multiple times";
        else if (exception instanceof MissingArgumentException)
            return "error: " + "you must enter an argument for " + ((MissingArgumentException) exception).getOption().getDescription();
        else if (exception instanceof MissingOptionException)
            return "error: " + "missing " + getMissingOptionsString((MissingOptionException) exception);
        else if (exception instanceof UnrecognizedOptionException)
            return "error: " + "option " + ((UnrecognizedOptionException) exception).getOption() + " isn't recognized";
        return exception.getMessage();
    }

    public static String getMissingOptionsString(MissingOptionException exception) {
        String result = "";
        for (Object missingOption : exception.getMissingOptions())
            result = ((Option) missingOption).getDescription() + ", ";
        result = result.replaceAll(", $", "");
        return result;
    }

}
