package kafka.generic.consumers;

import influx.InfluxDAO;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Logging;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GenericRunnableInfluxConsumer<K,V> extends GenericConsumer<K,V> implements Runnable {
    private String database;
    private InfluxDAO influxDAO;
    private Function<ConsumerRecords, List<Point>> recordsToPoints;


    /**
     * Create a generic runnable consumer that interacts with an influx database.
     * @param influxURL URL of the influx database
     * @param database name of the database to interact with
     * @param topics a list of topics for which this consumer will listen
     * @param bootstrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param keyDeserializerClass Class with which to serialize/deserialize record keys
     * @param valueDeserializerClass Class with which to serialize/deserialize record values
     * @param enableAutoCommit whether or not autoCommits are enabled
     * @param autoCommitIntervalMS interval between auto-commits
     * @param recordsToPoints a function which converts kafka ConsumerRecords to Influx Points
     */
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         List<String> topics, String bootstrapServer, String groupId,
                                         Object keyDeserializerClass, Object valueDeserializerClass,
                                         boolean enableAutoCommit, int autoCommitIntervalMS,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {

        // constructor call for super class
        super(topics, bootstrapServer, groupId,
                keyDeserializerClass, valueDeserializerClass,
                enableAutoCommit, autoCommitIntervalMS);

        // special variables
        this.database = database;
        this.influxDAO = new InfluxDAO(influxURL);
        this.recordsToPoints = recordsToPoints;
    }

    /**
     * Create a generic runnable consumer that interacts with an influx database.
     * @param influxURL URL of the influx database
     * @param database name of the database to interact with
     * @param topic a single topic for which this consumer will listen
     * @param bootstrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param keyDeserializerClass Class with which to serialize/deserialize record keys
     * @param valueDeserializerClass Class with which to serialize/deserialize record values
     * @param enableAutoCommit whether or not autoCommits are enabled
     * @param autoCommitIntervalMS interval between auto-commits
     * @param recordsToPoints a function which converts kafka ConsumerRecords to Influx Points
     */
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         String topic, String bootstrapServer, String groupId,
                                         Object keyDeserializerClass, Object valueDeserializerClass,
                                         boolean enableAutoCommit, int autoCommitIntervalMS,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // overloaded constructor
        this(influxURL, database, Collections.singletonList(topic), bootstrapServer, groupId,
             keyDeserializerClass, valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS, 
             recordsToPoints);

    }

    // constructors with sensible defaults

    /**
     * Generic runnable Influx Consumer with sensible defaults
     * @param influxURL URL of the influx database
     * @param database name of the database to interact with
     * @param topics a list of topics for which this consumer will listen
     * @param bootstrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param recordsToPoints a function which converts kafka ConsumerRecords to Influx Points
     */
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         List<String> topics, String bootstrapServer, String groupId,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // super constructor call
        super(topics, bootstrapServer, groupId);

        // special variables
        this.database = database;
        this.influxDAO = new InfluxDAO(influxURL);
        this.recordsToPoints = recordsToPoints;
    }

    /**
     * Generic runnable Influx Consumer with sensible defaults
     * @param influxURL URL of the influx database
     * @param database name of the database to interact with
     * @param topic a single topic for which this consumer will listen
     * @param bootstrapServer the server from which to poll Kafka data
     * @param groupId group ID for load distribution
     * @param recordsToPoints a function which converts kafka ConsumerRecords to Influx Points
     */
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         String topic, String bootstrapServer, String groupId,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // overloaded constructor
        this(influxURL, database, Collections.singletonList(topic), bootstrapServer, groupId, recordsToPoints);
    }


    // Run override for runnable class
    @Override
    public void run() {
        Logging.log("Starting runnable influx consumer on topics: "+ this.getTopics().toString());
        Logging.log("Inserting results into: "+ this.database);
        try {
            while (true) {
                // fetch records from Kafka Consumer
                ConsumerRecords records = getConsumer().poll(Duration.ofMillis(10));
                // convert records to points
                List<Point> batch = this.recordsToPoints.apply(records);
                // insert converted points into Influx
                getInfluxDAO().writePointList(database, batch);
            }
        } catch (Exception e) {
            Logging.error(e);
        }
    }

    private InfluxDAO getInfluxDAO() { return this.influxDAO; }
}
