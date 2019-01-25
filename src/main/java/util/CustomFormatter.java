package util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    }

}
