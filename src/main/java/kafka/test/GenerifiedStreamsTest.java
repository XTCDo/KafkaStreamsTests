package kafka.test;

import kafka.generic.streams.GenericStream;
import kafka.streams.ReverseRecordGeneric;

import java.util.concurrent.CountDownLatch;

public class GenerifiedStreamsTest {
    public static void main(String... varargs){

        GenericStream ReverseStream = new ReverseRecordGeneric("streams-generic-reverse","localhost:9092");

        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                ReverseStream.close();
                latch.countDown();
            }
        });

        try {
            // Start the kafka.streams application and stop it on ctrl+c
            ReverseStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
