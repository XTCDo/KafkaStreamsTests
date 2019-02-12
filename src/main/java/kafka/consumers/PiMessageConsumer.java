package kafka.consumers;

import com.google.gson.Gson;
import kafka.generic.consumers.GenericThreadedConsumer;
import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import planets.Planet;
import sun.rmi.runtime.Log;
import util.Config;
import util.Logging;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

                   records.forEach(record-> {
                       String values = record.value(); // confirmed OK -> is JSON
                       Map map = gson.fromJson(values, Map.class);
                       Logging.log("parsed JSON to map of size "+map.size(),TAG);
                       /*
                       Point point = Point.measurement("test-measurements")
                               .time(System.currentTimeMillis(),TimeUnit.MILLISECONDS)
                               .tag("mac-address",map.get("mac_address").toString())
                               .fields(map.get("atm_data"))
                               .
                       /*
                       Point point = Point.measurement("")
                               .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                               .tag(map.get("mac_address").toString())
                               .addField("name", getName())
                               .addField("capitol", getCapitol())
                               .addField("color", getColor())
                               .addField("gravity", getGravity())
                               .addField("dist_to_sun", getDistanceToSun())
                               .addField("temperature", getTemperature())
                               .build();


                       getInfluxDAO().writePoint("kafka_test", point);
                       */


                       Logging.log("received message: " + map.toString(), TAG);
                   });

                   Thread.sleep(2000);
               } catch (Exception e) {
                   Logging.error(e);
               }
           }
        });
        Logging.log("starting consumer", TAG);
        super.run(consumerThread);
    }
}
