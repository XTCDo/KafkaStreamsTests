package util;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggingFormatter extends Formatter {
    private static final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

    @Override
    public String format(LogRecord record) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder//.append("[")
                //.append(new Date(dateFormat.format(record.getMillis())))
                //.append("]")
                .append("[")
                .append(record.getLevel())
                .append("]");
        if(record.getParameters().length == 0){
            List<String> parameters = new ArrayList<>();
            Arrays.asList(record.getParameters()).forEach(value -> parameters.add(String.valueOf(value)));
            stringBuilder.append("[")
                    .append(String.join(" ", parameters))
                    .append("]");
        }
        stringBuilder.append(" ")
                .append(record.getMessage())
                .append("\n");
        return new String(stringBuilder);
    }
}
