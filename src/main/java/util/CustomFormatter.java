package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class CustomFormatter extends SimpleFormatter {
    // used formats
    private static final SimpleDateFormat TIMEFORMAT= new SimpleDateFormat("HH:mm:ss"); // formatting time

    @Override
    public synchronized String format(LogRecord record) {
             // first extract info form record
            String level = record.getLevel().toString();                    // logging level -> string
            String time = TIMEFORMAT.format(new Date(record.getMillis()));  // time variable, needs to be formatted
            String message = record.getMessage();                           // actual message

            String response;
            try {
                String tag = (String) record.getParameters()[0];
                response = buildFormat(level,time,tag,message);
            } catch (Exception e){
                response= buildFormat(level,time,message);
            }

            return response;
    }


    // formatting with Stringbuilder because it's significantly faster and more lightweight
    private String buildFormat(String level, String time, String tag, String message){
        return pad('[' + level + ']', 10, ' ') + // pad so messages are nicely aligned
                '<' + time + "> " +
                '[' + tag + "] " +
                ": " + message + '\n';
    }

    // do it again without tags
    private String buildFormat(String level, String time, String message){
        return pad('[' + level + ']', 10, ' ') + // pad so messages are nicely aligned
                '<' + time + "> " +
                ": " + message + '\n';
    }

    private String pad(String input, int length, char character){
        StringBuilder sb = new StringBuilder(input);
        while (sb.length()<length) sb.append(character);
        return sb.toString();
    }
}
