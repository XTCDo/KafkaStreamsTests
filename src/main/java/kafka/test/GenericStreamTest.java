package kafka.test;


import kafka.generic.streams.GenericStream;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.concurrent.CountDownLatch;

public class GenericStreamTest {

    public static void main(String... args){

        // Create a StreamsBuilder
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("streams-plaintext-input").to("streams-pipe-output");

        // now use that builder to make a generic Stream
        GenericStream pipeStream = new GenericStream<String, String>("streams-generic-pipe","localhost:9092",builder);


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
