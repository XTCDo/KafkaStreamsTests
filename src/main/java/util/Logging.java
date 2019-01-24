package util;

import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Logging {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());
    private static final String TAG = "logging";

    public Logging() {
        try {
            FileHandler logFile = new FileHandler("log%u.log");
            ConsoleHandler console = new ConsoleHandler();
            CustomFormatter customFormatter = new CustomFormatter();
            logFile.setFormatter(customFormatter);
            console.setFormatter(customFormatter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    // public functions
    /**
     * print a formatted string directly to System.out instead of a file
     * @param tag       tag for log sorting and filtering
     * @param message   log message
     */
    public static void logprint(String tag, String message){
        System.out.println(fullMessage(tag,message));
    }

    public void info(String tag, String message){log(Level.INFO, tag, message);}
    public void warn(String tag, String message){log(Level.WARNING, tag, message);}
    public void error(String tag, String message){log(Level.SEVERE, tag, message);}
    public void debug(String tag, String message){log(Level.FINE, tag, message);}
    public void trace(String tag, String message){log(Level.FINER, tag, message);}

    /**
     *  general log call for personal preference or other levels
     * @param level     logging level
     * @param tag       tag for log sorting and filtering
     * @param message   log message
     */
    public void log(Level level, String tag, String message){
        try {
            logger.log(level, message, tag);
        } catch (Exception e){
            error(TAG, "Error while logging: "+e.getMessage()+", defaulting to INFO.");
            info(tag, message);
        }
    }


    // private functions
    /**
     * converts circumstantial parameters to a timestamed, printable message
     * @param tag       tag for log sorting and filtering
     * @param message   log message
     * @return          String formatted "<time> [tag]: message"
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
