package util;

import java.io.IOException;
import java.util.logging.*;

public class Logging {
    static Logger logger;
    private Handler fileHandler;
    private Formatter plainTextFormatter;

    public Logging() {
        try {
            System.out.println(this.getClass().getClassLoader().getResource("logging.properties"));
            logger = Logger.getLogger(Logging.class.getName());
            fileHandler = new FileHandler("log", true);
            plainTextFormatter = new SimpleFormatter();
            fileHandler.setFormatter(plainTextFormatter);
            logger.addHandler(fileHandler);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
