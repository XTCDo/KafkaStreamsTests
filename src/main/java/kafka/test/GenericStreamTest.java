package kafka.test;


import kafka.generic.streams.GenericStream;
import kafka.streams.DefaultGenericStream;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.concurrent.CountDownLatch;

public class GenericStreamTest {

    public static void main(String... args){
        System.out.println("starting test");
        // now use that builder to make a generic Stream
        DefaultGenericStream pipeStream = new DefaultGenericStream("streams-generic-pipe","localhost:9092");


        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                pipeStream.close();
                latch.countDown();
            }
        });

        try {
            // Start the kafka.streams application and stop it on ctrl+c
            pipeStream.run();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);

    }

}
