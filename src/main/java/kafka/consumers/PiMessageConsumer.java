package kafka.consumers;

import com.google.gson.Gson;
import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PiMessageConsumer extends GenericThreadedInfluxConsumer<String, String> {
    private static final String TAG = "PiMessageConsumer";
    public PiMessageConsumer(){
        super("http://localhost:8086","python-input-alt", Config.getLocalBootstrapServersConfig(), "PiMessageConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            Gson gson = new Gson();

            while (true) {
                try {
                   ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));

                   records.forEach(record -> {

                       String values = record.value(); // confirmed OK -> is JSON
                       Map input = gson.fromJson(values, Map.class);

                       Logging.debug("got message containing: "+ input.entrySet().toString(),TAG);
                       try {
                           Point point = toPoint("test-measurements", input);
                           Logging.log("received data:" +point.toString(),TAG);
                           //getInfluxDAO().writePoint("Pi_Measurements", point);
                       } catch (Exception e){
                           Logging.error(e, TAG);
                       }
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

    private Point toPoint(String table, Map inputMap) throws Exception{
        // get standard info from input
        // todo learn about compute()
        /* expected input:
            {
                time: long (amount of milliseconds)
                tags: {tag1: value1, tag2:value2, ...}
                fields: {field1: value1, field2, value2,...}
            }
         */
        long time = Math.round(inputMap.get("time"));
        Map<String, String> tags = (Map<String, String>) inputMap.get("tags");
        Map<String, Object> fields = (Map<String, Object>)inputMap.get("fields");

        return Point.measurement(table)
                .time(time,TimeUnit.MILLISECONDS)
                .tag(tags)
                .fields(fields)
                .build();
    }

}
