package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class CustomFormatter extends SimpleFormatter {
    // used formats
    private static final String BASEFORMAT = "[%s] <%s>: %s\n";       // logging without tag
    private static final String TAGFORMAT = "[%s] <%s> [%s]: %s\n"; // logging with tag
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
                response = String.format(TAGFORMAT, level, time, tag, message);
            } catch (Exception e){
                response= String.format(BASEFORMAT,level,time, message);
            }

            return response;
    }

}
