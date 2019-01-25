package util;

import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.List;

public class SimpleLoggerTest {
    private static final String TAG = "SimpleLoggertest";

    public static void main(String[] args){
        List<Level> levels = new ArrayList<>();
        levels.add(Level.INFO);
        levels.add(Level.WARN);
        levels.add(Level.ERROR);
        levels.add(Level.DEBUG);
        levels.add(Level.TRACE);

        levels.forEach(level -> SimpleLogger.log(level,"testing "+level.getClass().getName()));
    }
}
