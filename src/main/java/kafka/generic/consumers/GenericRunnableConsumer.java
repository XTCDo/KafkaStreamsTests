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
public class GenericRunnableConsumer<K,V> extends GenericConsumer<K,V> implements Runnable {
    private Consumer<ConsumerRecords> lambda;

    // constructors

    /**
     * Create a generic runnable consumer.
     * @param topics a list of topics for which this consumer will listen
     * @param bootStrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param keyDeserializerClass Class with which to serialize/deserialize record keys
     * @param valueDeserializerClass Class with which to serialize/deserialize record values
     * @param enableAutoCommit whether or not autoCommits are enabled
     * @param autoCommitIntervalMS interval between auto-commits
     * @param recordsConsumer describes how to read data from records and what to do with it
     */
    public GenericRunnableConsumer(List<String> topics, String bootStrapServer, String groupId,
                                   Object keyDeserializerClass, Object valueDeserializerClass,
                                   boolean enableAutoCommit, int autoCommitIntervalMS,
                                   Consumer<ConsumerRecords> recordsConsumer) {

        super(topics, bootStrapServer, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS);

        this.lambda = recordsConsumer;
    }

    /**
     * Create a generic runnable consumer. Overload which describes only a single topic for which to listen.
     * @param topic a single topic for which this consumer will listen
     * @param bootStrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param keyDeserializerClass Class with which to serialize/deserialize record keys
     * @param valueDeserializerClass Class with which to serialize/deserialize record values
     * @param enableAutoCommit whether or not autoCommits are enabled
     * @param autoCommitIntervalMS interval between auto-commits
     * @param recordsConsumer describes how to read data from records and what to do with it
     */
    public GenericRunnableConsumer(String topic, String bootStrapServer, String groupId,
                                   Object keyDeserializerClass, Object valueDeserializerClass,
                                   boolean enableAutoCommit, int autoCommitIntervalMS,
                                   Consumer<ConsumerRecords> recordsConsumer) {

        this(Collections.singletonList(topic), bootStrapServer, groupId, keyDeserializerClass,
                valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS, recordsConsumer);
    }

    /**
     * A constructor with sensible defaults
     * @param topics a list of topics for which this consumer will listen
     * @param bootStrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param recordsConsumer describes how to read data from records and what to do with it
     */
    public GenericRunnableConsumer(List<String> topics,
                                   String bootStrapServer,
                                   String groupId,
                                   Consumer<ConsumerRecords> recordsConsumer) {

        super(topics, bootStrapServer, groupId);

        this.lambda = recordsConsumer;
    }

    /**
     * A constructor with sensible defaults. Overload which describes only a single topic for which to listen.
     * @param topic a single topic for which this consumer will listen
     * @param bootStrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param recordsConsumer describes how to read data from records and what to do with it
     */
    public GenericRunnableConsumer(String topic, 
                                   String bootStrapServer,
                                   String groupId,
                                   Consumer<ConsumerRecords> recordsConsumer) {
        this(Collections.singletonList(topic), bootStrapServer, groupId, recordsConsumer);
    }

    // Run override for runnable class
    @Override
    public void run() {
        Logging.log("Starting runnable consumer on topics: " + this.getTopics().toString());
        try {
            while (true) {
                // fetch records from Kafka Consumer
                ConsumerRecords records = getConsumer().poll(Duration.ofMillis(10));

                // perform the java consumer action on the records
                this.lambda.accept(records);
            }
        } catch (Exception e) {
            Logging.error(e);
        }
    }
}

