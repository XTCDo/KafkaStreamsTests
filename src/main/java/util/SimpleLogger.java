package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class SimpleLogger  {
    // okay so everything needs to be static
    private static Logger logger = LoggerFactory.getLogger("logger");

    // calls to logging
    public static void info(String message) {logger.info("{1}",message);}
    public static void debug(String message) {logger.debug(message);}
    public static void warn(String message) { logger.warn(message);}
    public static void error(String message) {logger.error(message);}
    public static void trace(String message) {logger.trace(message);}

    // generalized call for preference and utility
    public static void log(Level level, String message) throws IllegalArgumentException{
        switch (level){
            case INFO:
                info(message);
                break;
            case WARN:
                warn(message);
                break;
            case ERROR:
                error(message);
                break;
            case DEBUG:
                debug(message);
                break;
            case TRACE:
                trace(message);
                break;
            default:
                throw new IllegalArgumentException("Incorrect logging level");
        }


    }


}
