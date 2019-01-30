package util;

import java.io.IOException;
import java.util.logging.*;

public class Logging {
    static Logger logger;
    private Handler fileHandler;
    private Formatter formatter;

    public Logging() {
        try {
            System.out.println("Printing logging.properties location");
            System.out.println(this.getClass().getClassLoader().getResource("logging.properties"));
            logger = Logger.getLogger(Logging.class.getName());
            fileHandler = new FileHandler("log", true);
            formatter = new LoggingFormatter();
            fileHandler.setFormatter(formatter);
            /*
            plainTextFormatter = new SimpleFormatter();
            fileHandler.setFormatter(plainTextFormatter);
            */
            logger.addHandler(fileHandler);
            logger.setLevel(Level.FINEST);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
