package util.test;

import util.Logging;

import java.util.logging.Level;

public class TestLoggingStreams {
    public static void main(String[] args){
        Logging.log(Level.INFO, "Starting test");
        new TestLoggingProducer().run();
    }
}
