package kafka;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractThreadedProducer extends AbstractProducer {
    public AbstractThreadedProducer(String topic,
                                    String bootStrapServer,
                                    Object keySerializerClass,
                                    Object valueSerializerClass,
                                    String acks,
                                    int retries,
                                    int batchSize,
                                    int lingerMS,
                                    int bufferMemory){
        super(topic, bootStrapServer, keySerializerClass, valueSerializerClass,
                acks, retries, batchSize, lingerMS, bufferMemory);
    }

    public AbstractThreadedProducer(String topic, String bootStrapServer){
        super(topic, bootStrapServer);
    }

    public void run(Thread producerThread) {
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("producer-shutdown-hook"){
            @Override
            public void run(){
                getProducer().close();
                latch.countDown();
            }
        });

        try {
            producerThread.start();
            latch.await();
        } catch(Throwable e){
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

}
