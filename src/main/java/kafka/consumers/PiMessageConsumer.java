package kafka.consumers;

import com.google.gson.Gson;
import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PiMessageConsumer extends GenericThreadedInfluxConsumer<String, String> {
    private static final String TAG = "PiMessageConsumer";
    public PiMessageConsumer(){
        super("http://localhost:8086","python-input", Config.getLocalBootstrapServersConfig(), "PiMessageConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            Gson gson = new Gson();
            while (true) {
                try {
                   ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));

                   records.forEach(record -> {
                       String values = record.value(); // confirmed OK -> is JSON

                       Map map = gson.fromJson(values, Map.class);
                       Logging.debug("got message containing: "+ map.entrySet().toString(),TAG);

                       String mac_address = map.get("mac_address").toString();
                       // flatten the map into an entrySet

                       Set<Map.Entry> flatSet = (Set<Map.Entry>) flatten(map.entrySet().stream())
                               .filter(entry-> !((Map.Entry)entry).getKey().equals("mac_address"))
                               .collect(Collectors.toSet());

                       // cast entrySet
                       Map fieldmap = flatSet.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                       Point point = Point.measurement("test-measurements")
                               .time(System.currentTimeMillis(),TimeUnit.MILLISECONDS)
                               .tag("mac-address",mac_address)
                               .fields(fieldmap)
                               .build();

                       Logging.debug("converted to point: "+point.toString());

                       /*
                        // extract atmospheric data
                       Map atm_data = (Map) map.get("atmospheric_data");
                       Logging.debug("atmospheric data: " + atm_data.toString(),TAG);

                       // extract inertia data and delegate to sub-maps
                       Map inertia_data = (Map) map.get("inertia_data");
                       // accelerometer stuff
                       Map accelerometer_data = (Map) inertia_data.get("accelerometer");
                       Logging.debug("accelerometer data: " + accelerometer_data.toString(),TAG);
                       // gyroscope stuff
                       Map gyroscope_data = (Map) inertia_data.get("gyroscope");
                       Logging.debug("gyroscope data: " + gyroscope_data.toString(),TAG);
                        
                       Point point = Point.measurement("test-measurements")
                               .time(System.currentTimeMillis(),TimeUnit.MILLISECONDS)
                               .tag("mac-address", map.get("mac_address").toString())
                               .fields(atm_data)
                               .fields(accelerometer_data)
                               .fields(gyroscope_data)
                               .build();

                       Logging.debug("converted to Point: " + point.toString(),TAG);


                       getInfluxDAO().writePoint("Pi_Measurements", point);
                       Logging.log("written to database",TAG);
                       */
                   });

                   Thread.sleep(1000);
               } catch (Exception e) {
                   Logging.error(e);
               }
           }
        });
        Logging.log("starting consumer", TAG);
        super.run(consumerThread);
    }

    private Stream<Map.Entry> flatten(Stream<Map.Entry> input){
        return input.flatMap(entry -> {
            if (entry.getValue() instanceof Map){
                return flatten(((Map) entry.getValue()).entrySet().stream());
            }
            else {
                return Stream.of( entry);
            }
        });
    }

}
