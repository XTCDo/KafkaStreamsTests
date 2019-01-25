package kafka.generic.consumers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
// TODO Test generic consumers
public class GenericThreadedConsumer<K, V> extends GenericConsumer<K, V> {
    public GenericThreadedConsumer(List<String> topics,
                                   String bootStrapServers,
                                   String groupId,
                                   Object keyDeserializerClass,
                                   Object valueDeserializerClass,
                                   boolean enableAutoCommit,
                                   int autoCommitIntervalMS){
        super(topics, bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);
    }

    public GenericThreadedConsumer(String topic,
                                   String bootStrapServers,
                                   String groupId,
                                   Object keyDeserializerClass,
                                   Object valueDeserializerClass,
                                   boolean enableAutoCommit,
                                   int autoCommitIntervalMS){
        super(Arrays.asList(topic), bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);
    }

    public GenericThreadedConsumer(List<String> topics, String bootStrapServer, String groupId){
        super(topics, bootStrapServer, groupId);
    }

    public GenericThreadedConsumer(String topic, String bootStrapServer, String groupId){
        super(Arrays.asList(topic), bootStrapServer, groupId);
    }

    public void run(Thread consumerThread) {
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("consumer-shutdown-hook"){
            @Override
            public void run(){
                getConsumer().close();
                latch.countDown();
            }
        });

        try {
            consumerThread.start();
            latch.await();
        } catch(Throwable e){
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
