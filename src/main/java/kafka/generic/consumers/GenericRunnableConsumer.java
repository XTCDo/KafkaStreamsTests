package kafka.generic.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Logging;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * a class which may or may not replace GenericThreadedConsumer in the future
 * at creation, it behaves exactly like GenericThreadedConsumer with the exception that it accepts
 * java consumers (lambda functions) and exectutes them in a thread
 */
public class GenericRunnableConsumer<K,V> extends GenericConsumer<K,V> implements Runnable{
    private List<Consumer<ConsumerRecords>> lambdas = new ArrayList<>(); // these are java consumers, NOT kafka consumers

    // constructors

    // todo documentation
    public GenericRunnableConsumer(List<String> topics,
                                         String bootStrapServers,
                                         String groupId,
                                         Object keyDeserializerClass,
                                         Object valueDeserializerClass,
                                         boolean enableAutoCommit,
                                         int autoCommitIntervalMS,
                                         Consumer<ConsumerRecords> ...recordsConsumers){

        super(topics, bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);

        // set up lambdas
        this.lambdas.addAll(Arrays.asList(recordsConsumers));
    }

    public GenericRunnableConsumer(String topic,
                                   String bootStrapServers,
                                   String groupId,
                                   Object keyDeserializerClass,
                                   Object valueDeserializerClass,
                                   boolean enableAutoCommit,
                                   int autoCommitIntervalMS,
                                   Consumer<ConsumerRecords> ...recordsConsumers) {
        this(Collections.singletonList(topic), bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS, recordsConsumers);
    }

    // constructors from super with sensible defaults

    public GenericRunnableConsumer(List<String> topics, String bootStrapServer, String groupId, Consumer<ConsumerRecords> ...recordsConsumers){
        super(topics, bootStrapServer, groupId);
        // this.lambdas = List.of(recordsConsumers); // language level 9
        this.lambdas.addAll(Arrays.asList(recordsConsumers));
    }

    public GenericRunnableConsumer(String topic, String bootStrapServer, String groupId, Consumer<ConsumerRecords> ... recordsConsumers){
        this(Collections.singletonList(topic), bootStrapServer, groupId, recordsConsumers);
    }
    // Run override for runnable class

    @Override
    public void run() {
        Logging.log("Starting runnable consumer on topics: "+ this.getTopics().toString());
        try{
            while (true) {
                // fetch records from Kafka Consumer
                ConsumerRecords records = getConsumer().poll(Duration.ofMillis(10));

                // perform all of the Java Consumers to process the fetched records
                for (Consumer<ConsumerRecords> lambdaFunction : this.lambdas) {
                    lambdaFunction.accept(records);
                }
            }
        } catch (Exception e){
            Logging.error(e);
        }
    }
}

