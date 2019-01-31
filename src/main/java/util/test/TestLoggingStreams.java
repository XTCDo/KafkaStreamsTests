package util.test;

import util.Logging;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

public class TestLoggingStreams {
    public static void main(String[] args){
        final String TAG = "TestLoggingStreams.main";
        Logging.log(Level.INFO, "Creating testLoggingProducer", TAG);
        TestLoggingProducer testLoggingProducer = new TestLoggingProducer();
        Logging.log(Level.INFO, "Creating testLoggingConsumer");
        TestLoggingConsumer testLoggingConsumer = new TestLoggingConsumer();

        Logging.log(Level.INFO, "Starting testLoggingProducer");
        testLoggingProducer.run();
        Logging.log(Level.INFO, "Starting testLoggingConsumer");
        testLoggingConsumer.run();
    }
}
