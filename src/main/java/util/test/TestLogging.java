package util.test;

import util.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogging {
    static Logger logger;
    public static void main(String[] args) {
        Map<Level, String> logThis = new HashMap<Level, String>();
        logThis.put(Level.SEVERE, "This is severe");
        logThis.put(Level.WARNING, "This is warning");
        logThis.put(Level.INFO, "This is info");
        logThis.put(Level.CONFIG, "This is config");
        logThis.put(Level.FINE, "This is fine");
        logThis.put(Level.FINER, "This is finer");
        logThis.put(Level.FINEST, "This is finest");
        logger = Logger.getLogger(Logging.class.getName());
        logger.setLevel(Level.ALL);
        logThis.forEach((key, value) -> {logger.log(key, String.format("%s: %s", key.getName(), value));});

        System.out.println(logger.getClass());
        System.out.println(logger.getHandlers());

        Thread threadOne = new Thread(() -> {
            Logger loggerOne = Logger.getLogger(Logging.class.getName());
            List<String> list = new ArrayList<>();
            for(int i = 0; i < 100; i++){
                list.add("one 1");
                list.add("one 2");
            }

            list.forEach((value) -> loggerOne.log(Level.INFO, value));
        });

        Thread threadTwo = new Thread(() -> {
            Logger loggerTwo = Logger.getLogger(Logging.class.getName());
            for(int i = 0; i < 50; i++){
                loggerTwo.log(Level.INFO, String.valueOf(i));
                try {
                    Thread.sleep(10);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}
