package kafka.generic.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Logging;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * a class which may or may not replace GenericThreadedConsumer in the future
 * at creation, it behaves exactly like GenericThreadedConsumer with the exception that it accepts
 * java consumers (lambda functions) and executes them in a thread
 */
public class GenericRunnableConsumer<K,V> extends GenericConsumer<K,V> implements Runnable{
    private Consumer<ConsumerRecords> lambda;

    // constructors

    // todo documentation
    public GenericRunnableConsumer(List<String> topics, String bootStrapServers, String groupId,
                                   Object keyDeserializerClass, Object valueDeserializerClass,
                                   boolean enableAutoCommit, int autoCommitIntervalMS,
                                   Consumer<ConsumerRecords> recordsConsumer){

        super(topics, bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);

        // set up lambdas
        this.lambda = recordsConsumer;
    }

    public GenericRunnableConsumer(String topic, String bootStrapServers, String groupId,
                                   Object keyDeserializerClass, Object valueDeserializerClass,
                                   boolean enableAutoCommit, int autoCommitIntervalMS,
                                   Consumer<ConsumerRecords> recordsConsumer) {

        this(Collections.singletonList(topic), bootStrapServers, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS, recordsConsumer);
    }

    // constructors from super with sensible defaults

    public GenericRunnableConsumer(List<String> topics, String bootStrapServer, String groupId, Consumer<ConsumerRecords> recordsConsumer){
        super(topics, bootStrapServer, groupId);

        this.lambda = recordsConsumer;
    }

    public GenericRunnableConsumer(String topic, String bootStrapServer, String groupId, Consumer<ConsumerRecords> recordsConsumer){
        this(Collections.singletonList(topic), bootStrapServer, groupId, recordsConsumer);
    }

    // Run override for runnable class
    @Override
    public void run() {
        Logging.log("Starting runnable consumer on topics: "+ this.getTopics().toString());
        try{
            while (true) {
                // fetch records from Kafka Consumer
                ConsumerRecords records = getConsumer().poll(Duration.ofMillis(10));

                // perform the java consumer action on the records
                this.lambda.accept(records);
            }
        } catch (Exception e){
            Logging.error(e);
        }
    }
}

