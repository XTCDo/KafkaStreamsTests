package kafka.consumers;

import com.google.gson.Gson;
import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VaillantConsumer extends GenericThreadedInfluxConsumer<String, String> {
    static final String topic = "vaillant-input";
    static final String influxURL = "http://localhost:8086";
    static final String TAG = "VaillantConsumer";
    public VaillantConsumer(){
        super(influxURL, topic, Config.getLocalBootstrapServersConfig(), "VaillantConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            Gson gson = new Gson();
            try {
                while(true) {
                    ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofMillis(10));

                    for(ConsumerRecord<String, String> record : records){
                        //Logging.log(record.toString(), TAG);
                        Map vaillantData = gson.fromJson(record.value().toString(), Map.class);
                        Logging.log(vaillantData.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                getInfluxDAO();
                Logging.error(e, TAG);
            }
        });
        super.run(consumerThread);
    }

}
