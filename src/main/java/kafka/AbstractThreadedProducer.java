package kafka;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractThreadedProducer<K,V> extends AbstractProducer {
    private Thread producerThread;

    public AbstractThreadedProducer(Thread producerThread,
                                    String topic,
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
        this.producerThread = producerThread;
    }

    public AbstractThreadedProducer(Thread producerThread, String topic, String bootStrapServer){
        super(topic, bootStrapServer);
        this.producerThread = producerThread;
    }

    public void run(){
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
