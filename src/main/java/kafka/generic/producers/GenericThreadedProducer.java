package kafka.generic.producers;

import java.util.concurrent.CountDownLatch;

/**
 * TODO needs docs
 * @param <K>
 * @param <V>
 */
public class GenericThreadedProducer<K, V> extends GenericProducer<K, V> {
    public GenericThreadedProducer(String topic,
                                   String bootStrapServer,
                                   Object keySerializerClass,
                                   Object valueSerializerClass,
                                   String acks,
                                   int retries,
                                   int batchSize,
                                   int lingerMS,
                                   int bufferMemory) {
        super(topic, bootStrapServer, keySerializerClass, valueSerializerClass,
                acks, retries, batchSize, lingerMS, bufferMemory);
    }

    public GenericThreadedProducer(String topic, String bootStrapServer) {
        super(topic, bootStrapServer);
    }

    public void run(Thread producerThread) {
        try {
            producerThread.start();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

}
