package util;

import sun.util.logging.LoggingSupport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    // used formats
    private static final String BASEFORMAT = "[%s] <%s>: %s";       // logging without tag
    private static final String TAGFORMAT = "[%s] <%s> [%s]: %s"; // logging with tag
    private static final SimpleDateFormat TIMEFORMAT= new SimpleDateFormat("HH:mm:ss"); // formatting time

    @Override
    public String format(LogRecord record) {

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
