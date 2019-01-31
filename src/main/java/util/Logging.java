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

    public static void log(Level level, String message){
        getLogger().log(level, message);
    }

    public static void log(Level level, String message, Object param1){
        getLogger().log(level, message, param1);
    }

    public static void log(Level level, String message, Object[] params){
        getLogger().log(level, message, params);
    }
}
