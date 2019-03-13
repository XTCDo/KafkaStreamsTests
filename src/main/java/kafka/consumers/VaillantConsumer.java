package kafka.consumers;

import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Config;
import util.Logging;

import java.time.Duration;

public class VaillantConsumer extends GenericThreadedInfluxConsumer<String, String> {
    static final String topic = "vaillant-input";
    static final String influxURL = "http://localhost:8086";

    public VaillantConsumer(){
        super(influxURL, topic, Config.getLocalBootstrapServersConfig(), "VaillantConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            try {
                while(true) {
                    ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofMillis(10));

                    for(ConsumerRecord<String, String> record : records){
                        Logging.log(record.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                getInfluxDAO();
                Logging.error(e);
            }
        });
        super.run(consumerThread);
    }

}
