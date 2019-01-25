package util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;


public class Logging {
    private Logger logger = Logger.getLogger(Logging.class.getName()); // todo figure out possibility for custom file logging
    private static final String TAG = "logging";
    private static final String logDirPath="./logs/";


    // constructors
    public Logging() {
        new Logging(true, true);
    }
    public Logging(boolean console, boolean file){
        //define default value for file Handling
        String defaultFileName="";
        if (file) {
            String timeString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            defaultFileName = "log-" + timeString;
        }
        new Logging(console, file, defaultFileName);
    }

    // this function will ALWAYS be called on creating a new object
    // logging to a custom file is temporarily broken: todo implement diversified logging.
    public Logging(boolean console, boolean file, String fileName){
        // I reject your handlers, and susbtitute my own!
        logger.setUseParentHandlers(false);

        if (console){logger.addHandler(consoleHandler());} // consoleHandlers are easy
        if (file){
            try {
                logger.addHandler(fileHandler(logDirPath,fileName));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // it is possible for a logger to not have any handlers at all
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
    public void severe(String tag, String message){log(Level.SEVERE, tag, message);}
    public void fine(String tag, String message){log(Level.FINE, tag, message);}
    public void finer(String tag, String message){log(Level.FINER, tag, message);}
    // aliases
    public void error(String tag, String message){severe(tag, message);}
    public void debug(String tag, String message){fine(tag, message);}
    public void trace(String tag, String message){finer(tag, message);}

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

    /**
     * tagless logging
     * @param level     log level
     * @param message   log message
     */
    public void log(Level level, String message){
        try {
            logger.log(level, message);
        } catch (Exception e){
            error(TAG, "Error while logging: "+e.getMessage()+", defaulting to INFO.");
            log(Level.INFO, message);
        }
    }


    // private functions
    private FileHandler fileHandler(String filePath, String fileName) throws Exception{
            // catch user errors
            if (filePath.isEmpty() || fileName.isEmpty()){
                throw new IllegalArgumentException("file path or file name can't be empty");
            }

            // ensure there's a folder to write logs to
            File path = new File(filePath);
            if (!(path.exists())) { path.mkdir();}

            // then, create a handler to the specified file - append if it already exists
            FileHandler fileHandler = new FileHandler(filePath + fileName + ".log", true); // IOException is propagated upwards
            fileHandler.setFormatter(new CustomFormatter());// special: set a custom formatter for tagging
            return fileHandler;
    }

    private ConsoleHandler consoleHandler(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        return consoleHandler;
    }

    /**
     * converts circumstantial parameters to a timestamped, printable message
     * @param tag       tag for log sorting and filtering
     * @param message   log message
     * @return          String formatted "[CONSOLE] <time> [tag]: message"
     */
    private static String fullMessage(String tag, String message){
        return String.format("%10s","[CONSOLE]")+'<'+now()+"> "+'['+tag+"]: "+message;
    }

    private static String now(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    @Override
    protected void finalize() throws Throwable {
        // close all current fileHandlers
        Arrays.stream(logger.getHandlers())
                .filter(handler -> handler instanceof FileHandler)
                .forEach(Handler::close);

        super.finalize();
    }
}
