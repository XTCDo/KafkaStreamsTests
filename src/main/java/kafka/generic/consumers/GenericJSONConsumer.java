package kafka.generic.consumers;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Logging;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// todo implement runnable (In GenericThreadedConsumer)
public class GenericJSONConsumer extends GenericThreadedInfluxConsumer<String, String> {

    protected String TAG;
    private Thread consumerThread;

   // constructors

    /**
     * construct a new GenericJsonConsumer, which will listen to a list of topics, and subsequently put the records from that topic into
     * an Influx database.
     * @param influxURL url for influxDB
     * @param topics topics on which to listen
     * @param bootStrapServer apache kafka broker server
     * @param groupId group ID for this consumer
     * @param database database in which to inject message data
     * @param measurement table inside database in which to inject message data
     * @param sleepDuration delay between thread operations
     */
    public GenericJSONConsumer(String influxURL, List<String> topics, String bootStrapServer, String groupId,
                               String database, String measurement, int sleepDuration) {
        super(influxURL, topics,  bootStrapServer, groupId);

        this.consumerThread= new Thread(()->{
            Logging.log("Starting consumer on topics: ["+String.join(", ",topics)+"]");
            Logging.log("Inserting into database: "+database+", measurement: "+measurement);

            while (true){
                try {
                    ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofMillis(10));

                    // process records into Influx
                    records.forEach(record -> {
                        // extract values from Record (this should be JSON)
                        Point point = JSONToPoint(record.value(), measurement);
                        // inject created point into Influx
                        getInfluxDAO().writePoint(database, point);
                    });

                    // sleep for a bit as to not torture processors
                    Thread.sleep(sleepDuration);
                } catch ( Exception e){
                    Logging.error(e, TAG);
                }
            }

        });
    }

    /**
     * construct a new GenericJsonConsumer, which will listen to a list of topics, and subsequently put the records from that topic into
     * an Influx database.
     * @param influxURL url for influxDB
     * @param topic single topic on which to listen
     * @param bootStrapServer apache kafka broker server
     * @param groupId group ID for this consumer
     * @param database database in which to inject message data
     * @param measurement table inside database in which to inject message data
     * @param sleepDuration delay between thread operations
     */
    public GenericJSONConsumer(String influxURL, String topic, String bootStrapServer, String groupId,
                               String database, String measurement, int sleepDuration) {
        this(influxURL, Collections.singletonList(topic), bootStrapServer, groupId, database, measurement, sleepDuration);
    }

    /**
     * construct a new GenericJsonConsumer, which will listen to a list of topics, and subsequently put the records from that topic into
     * an Influx database. Defaults to a sleepDuration of 100 ms.
     * @param influxURL url for influxDB
     * @param topics topics on which to listen
     * @param bootStrapServer apache kafka broker server
     * @param groupId group ID for this consumer
     * @param database database in which to inject message data
     * @param measurement table inside database in which to inject message data
     */
    public GenericJSONConsumer(String influxURL, List<String> topics, String bootStrapServer, String groupId,
                               String database, String measurement) {
        this(influxURL, topics , bootStrapServer, groupId, database, measurement, 1000);
    }

    /**
     * construct a new GenericJsonConsumer, which will listen to a list of topics, and subsequently put the records from that topic into
     * an Influx database. Defaults to a sleepDuration of 100 ms.
     * @param influxURL url for influxDB
     * @param topic single topic on which to listen
     * @param bootStrapServer apache kafka broker server
     * @param groupId group ID for this consumer
     * @param database database in which to inject message data
     * @param measurement table inside database in which to inject message data
     */
    public GenericJSONConsumer(String influxURL, String topic, String bootStrapServer, String groupId,
                               String database, String measurement) {
        this(influxURL, topic, bootStrapServer, groupId, database, measurement, 1000);
    }


    // methods

    /**
     * run the thread this consumer possesses
     */
    public void run(){
        super.run(consumerThread);
    }

    /**
     * convert input JSON to a point for Influx
     * @param JSONString string containing data for influx database (time(Unix timestamp), tags(Map of strings) & fields(Map of values))
     * @param measurement name of the table in which to inject this point
     * @return Point ready to inject into Influx
     */
    private Point JSONToPoint(String JSONString, String measurement){
        Gson gson = new Gson();
        Map values = gson.fromJson(JSONString, Map.class);

        // extract time and convert double representing seconds to long representing milliseconds
        long time = Double.valueOf((double)values.get("time")*1000).longValue();

        // extract tags
        Map<String, String> tags = new HashMap<>();
        ((Map) values.get("tags")).forEach((key, value)->tags.put((String)key, String.valueOf(value)));

        // extract values
        Map<String, Object> fields = (Map<String, Object>) values.get("fields");

        // build and return a point
        return Point.measurement(measurement)
                .time(time, TimeUnit.MILLISECONDS)
                .tag(tags)
                .fields(fields)
                .build();
    }
}

