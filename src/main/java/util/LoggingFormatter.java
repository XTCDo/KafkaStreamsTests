package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

public class LoggingFormatter extends Formatter {
    /**
     * The date format we want to use
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

    /**
     * Function that formats LogRecords the way we want it
     * @param record The LogRecord that has to be formatted
     * @return The LogRecord, but formatted and as a String
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder stringBuilder = new StringBuilder();

        // Add the date and the level surrounded by straight braces
        stringBuilder.append("[").append(dateFormat.format(new Date())).append("]")
                .append("[").append(record.getLevel()).append("]");

        // Only add tags if there are any
        if(record.getParameters() != null) {
            List<String> parameters = new ArrayList<>();
            Arrays.asList(record.getParameters()).forEach(value -> parameters.add(String.valueOf(value)));
            stringBuilder.append("[").append(String.join(" | ", parameters)).append("]");
        }

        // Then, add the logging message
        stringBuilder.append(": ").append(record.getMessage()).append("\n");

        // if an error was logged, append this to the message
        if (record.getThrown() != null) {
            // first element of stacktrace is always error message
            stringBuilder.append("\t").append(record.getThrown().getMessage()).append(" at: \n\t")
            // follow up with a listing of stack trace elements
            .append(Arrays.stream(record.getThrown().getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n\t")))
                    .append("\n");
        }

        // Return our beautiful string
        return new String(stringBuilder);
    }
}
