package util;

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


    /**
     * log a message with these parameters, base for other util functions in this class
     * @param level     logger level
     * @param message   message to send to logger
     * @param params    logging tags
     */
    public static void log(Level level, String message, Object ...params){
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
     * log an error with a header message
     * @param message   log message
     * @param thrown    thrown error
     */
    public static void error(String message, Throwable thrown, Object ...params){
        log(Level.SEVERE, message, thrown, params);
    }

    /**
     * log an error without message
     * @param thrown what went wrong
     */
    public static void error(Throwable thrown, Object ...params){
        log(Level.SEVERE, "Error found at: ", thrown, params);
    }

    /**
     * log a warning
     * @param message   logging message
     * @param params    logging tags
     */
    public static void warn(String message, Object ...params){
        log(Level.WARNING, message, params);
    }

    /**
     * log a debug message
     * @param message   logging message
     * @param params    logging tags
     */
    public static void debug(String message, Object ...params){
        log(Level.FINE, message, params);
    }


}
