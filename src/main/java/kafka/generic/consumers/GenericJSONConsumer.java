package kafka.generic.consumers;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// todo implement runnable (In GenericThreadedConsumer)
public class GenericJSONConsumer extends GenericThreadedInfluxConsumer<String, String> {

    protected String TAG;
    protected String database, measurement;
    private Thread consumerThread;

    public GenericJSONConsumer(String influxURL, List<String> topics,String bootStrapServer, String groupId, String database, String measurement) {
        super(influxURL, topics,  bootStrapServer, groupId);

        this.consumerThread= new Thread(()->{

            while (true){
                ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));
                // process records into Influx
                records.forEach(record -> {
                    try{
                        // extract values from Record (this should be JSON)
                        Map values = gson.fromJson(record.value(), Map.class);

                        // extract time
                        Date time = values.get("time");

                        // extract tags
                        List<String> tags = values.get("tags");

                        // extract values
                        Map fields = values.get("fields");

                        // build a point
                        Point point = Point.measurement(measurement)
                                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                .tag(tags)
                                .fields(fields)
                                .build();

                        getInfluxDAO().writePoint(database, point);

                        Thread.sleep(2000);
                    }catch (Exception e){
                        Logging.error(e);
                    }
                });
            }
        });
    }

    public GenericJSONConsumer(String influxURL, String topic,String bootStrapServer, String groupId) {
        super(influxURL, topic,  bootStrapServer, groupId);
    }

    public void run(){
        super.run(consumerThread);
    }

    private Point JSONToPoint(String JSONString, String measurement){
        Gson gson = new Gson();
        Map values = gson.fromJson(JSONString, Map.class);

        // extract time (always in milliseconds)
        long time = Double.valueOf((double)values.get("time")).longValue(); // todo test time units

        // extract tags
        Map<String, String> tags = new HashMap<>();
        ((Map) values.get("tags")).forEach((key, value)->tags.put((String)key, String.valueOf(value))); // retarded java syntax

        // extract values
        Map<String, Object> fields = (Map<String, Object>) values.get("fields");

        // build and return a point
        return Point.measurement(measurement)
                .time(time, TimeUnit.MILLISECONDS) // todo test time units
                .tag(tags)
                .fields(fields)
                .build();
    }
}

