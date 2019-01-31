package kafka.test;


import kafka.streams.DefaultGenericStream;

import java.util.concurrent.CountDownLatch;

public class GenericStreamTest {

    public static void main(String... args){
        System.out.println("starting test");

        // create some Streams
        DefaultGenericStream pipe1 = new DefaultGenericStream("streams-generic-pipe","localhost:9092");
        DefaultGenericStream pipe2 = new DefaultGenericStream("streams-generic-pipe","localhost:9092");
        DefaultGenericStream pipe3 = new DefaultGenericStream("streams-generic-pipe","localhost:9092");
        DefaultGenericStream pipe4 = new DefaultGenericStream("streams-generic-pipe","localhost:9092");
        DefaultGenericStream pipe5 = new DefaultGenericStream("streams-generic-pipe","localhost:9092");

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
