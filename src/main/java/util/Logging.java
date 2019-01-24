package util;

import org.apache.kafka.common.protocol.types.Field;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;



public class Logging {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());
    private static final String TAG = "logging";

    // public functions

    /**
     * print a formatted string directly to System.out instead of a file
     * @param tag
     * @param message
     */
    public static void logprint(String tag, String message){
        System.out.println(fullMessage(tag,message));
    }

    public static void info(String tag, String message){info(fullMessage(tag,message));}
    public static void warn(String tag, String message){warn(fullMessage(tag,message));}
    public static void error(String tag, String message){error(fullMessage(tag,message));}
    public static void debug(String tag, String message){debug(fullMessage(tag,message));}

    public static void info(String fullmessage){logger.info(fullmessage);}
    public static void warn(String fullmessage){logger.warning(fullmessage);}
    public static void error(String fullmessage){logger.severe(fullmessage);}
    public static void debug(String fullmessage){logger.fine(fullmessage);}


    /**
     *  general log call for personal preference or other levels
     * @param level
     * @param tag
     * @param message
     */
    public static void log(Level level, String tag, String message){
        String fullMessage = fullMessage(tag,message);
        try {
            logger.log(level, fullMessage);
        } catch (Exception e){
            error(fullMessage(TAG, "exception while trying to log, defaulting to info"));
            info(fullMessage);
        }
    }


    // private functions

    /**
     * converts circumstantial parameters to a timestamed, printable message
     * @param tag
     * @param message
     * @return
     */
    private static String fullMessage(String tag, String message){
        return new StringBuilder()
                .append("<").append(now()).append("> ")
                .append("[").append(tag).append("]: ")
                .append(message)
                .toString();
    }

    private static String now(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
