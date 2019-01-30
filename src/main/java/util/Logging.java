package util;

import java.io.IOException;
import java.util.logging.*;

public class Logging {
    static Logger logger;

    private Logging() throws IOException {
        logger = Logger.getLogger(Logging.class.getName());
        logger.setUseParentHandlers(false);

        FileHandler fileHandler = new FileHandler("logs/log", true);
        fileHandler.setFormatter(new LoggingFormatter());
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LoggingFormatter());
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        logger.setLevel(Level.ALL);
    }

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
