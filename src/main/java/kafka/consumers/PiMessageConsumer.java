package kafka.consumers;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import planets.Planet;
import sun.rmi.runtime.Log;
import util.Config;
import util.Logging;

import java.time.Duration;

public class PiMessageConsumer extends GenericThreadedConsumer<String, String> {
    private static final String TAG = "PiMessageConsumer";
    public PiMessageConsumer(){
        super("python-input", Config.getLocalBootstrapServersConfig(), "PiMessageConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            while (true) {
                Logging.debug("fetching info", TAG);
                try {
                   ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));
                   Logging.debug("fetched "+ records.count()+" records");

                   records.forEach(record-> {
                       String values = record.value();
                       Logging.log("received message: " + values, TAG);
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
