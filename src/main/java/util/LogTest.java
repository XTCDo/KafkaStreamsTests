package util;


import java.util.logging.Logger;

public class LogTest {
    private static final Logger logger = Logger.getLogger("logger");

    private static void main(String[] args ){
        for (int i=0; i< 10; i++){
            logger.info("test");
        }
    }
}
