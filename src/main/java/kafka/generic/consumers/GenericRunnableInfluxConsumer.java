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

    // todo documentation
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         List<String> topics, String bootStrapServers, String groupId,
                                         Object keyDeserializerClass, Object valueDeserializerClass,
                                         boolean enableAutoCommit, int autoCommitIntervalMS,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {

        // constructor call for super class
        super(topics, bootStrapServers, groupId,
                keyDeserializerClass, valueDeserializerClass,
                enableAutoCommit, autoCommitIntervalMS);

        // special variables
        this.database = database;
        this.influxDAO = new InfluxDAO(influxURL);
        this.recordsToPoints = recordsToPoints;
    }
    
    // todo documentation
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         String topic, String bootStrapServers, String groupId,
                                         Object keyDeserializerClass, Object valueDeserializerClass,
                                         boolean enableAutoCommit, int autoCommitIntervalMS,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // overloaded constructor
        this(influxURL, database, Collections.singletonList(topic), bootStrapServers, groupId,
             keyDeserializerClass, valueDeserializerClass, enableAutoCommit, autoCommitIntervalMS, 
             recordsToPoints);

    }

    // with sensible defaults
    // todo documentation
    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         List<String> topics, String bootStrapServer, String groupId,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // super constructor call
        super(topics, bootStrapServer, groupId);

        // special variables
        this.database = database;
        this.influxDAO = new InfluxDAO(influxURL);
        this.recordsToPoints = recordsToPoints;
    }

    public GenericRunnableInfluxConsumer(String influxURL, String database,
                                         String topics, String bootStrapServer, String groupId,
                                         Function<ConsumerRecords, List<Point>> recordsToPoints) {
        // overloaded constructor
        this(influxURL, database, Collections.singletonList(topics), bootStrapServer, groupId, recordsToPoints);
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
