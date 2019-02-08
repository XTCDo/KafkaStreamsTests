package util;

import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.connect.data.SchemaProjector;

import javax.sound.sampled.LineEvent;
import java.io.IOException;
import java.util.logging.*;

public class Logging {
    static Logger logger;

    /**
     * Constructor for Logging to create logger
     * @throws IOException
     */
    private Logging() throws IOException {
        // No clue what this does
        logger = Logger.getLogger(Logging.class.getName());
        // Do not use the parent handlers
        logger.setUseParentHandlers(false);

        // Create a FileHandler and make it place the logs in a logs folder
        FileHandler fileHandler = new FileHandler("logs/log", true);
        // Use our own custom LoggingFormatter for the logs
        fileHandler.setFormatter(new LoggingFormatter());
        // Log everything
        fileHandler.setLevel(Level.ALL);
        // Add log handler to logger
        logger.addHandler(fileHandler);

        // Create a ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // Use our own custom LoggingFormatter
        consoleHandler.setFormatter(new LoggingFormatter());
        // Log everything
        consoleHandler.setLevel(Level.ALL);
        // Add log handler to logger
        logger.addHandler(consoleHandler);

        // Make sure we log everything for good measure
        logger.setLevel(Level.ALL);
    }

    /**
     * Getter for the logger that calls the constructor for Logging if there is no logger
     * @return The logger
     */
    private static Logger getLogger(){
        if(logger == null){
            try {
                new Logging();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return logger;
    }


    // todo: evaluate wether or not this function is redundant due to ...params
    public static void log(Level level, String message){
        getLogger().log(level, message);
    }

    public static void log(Level level, String message, Object param1){
        getLogger().log(level, message, param1);
    }

    public static void log(Level level, String message, Object[] params){
        getLogger().log(level, message, params);
    }

    /**
     * for logging errors WITH tags
     * @param level logging level (preferably SEVERE)
     * @param message message to log
     * @param thrown throwable object
     * @param params logging tags
     */
    public static void log(Level level, String message, Throwable thrown, Object ... params){
        LogRecord record = new LogRecord(level, message);
        record.setParameters(params);
        record.setThrown(thrown);
        getLogger().log(record);
    }

    public static void log(Level level, String message, Throwable err ){
        getLogger().log(level, message, err);
    }

    // above functions overloaded to sensible defaults

    /**
     * default log will print to info level
     * @param message   log message
     * @param params    log parameters
     */
    public static void log(String message, Object ...params){
        log(Level.INFO, message, params);
    }

    /**
     * default errors happen at severe level
     * @param message   log message
     * @param thrown       object of instance throwable
     */
    public static void error(String message, Throwable thrown){
        log(Level.SEVERE, message, thrown);
    }

    /**
     * log a throwable object and nothing else
     * @param thrown what went wrong
     */
    public static void error(Throwable thrown){
        error("An error occurred, printing stacktrace below:",thrown);
    }

}
