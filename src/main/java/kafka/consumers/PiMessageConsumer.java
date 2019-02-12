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

    public PiMessageConsumer(){
        super("python-input", Config.getLocalBootstrapServersConfig(), "PiMessageConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
           try {
               ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));
               records.forEach(record-> {
                   String values = record.value();
                   Logging.log("received message: " + values);
               });
               Thread.sleep(100);
           } catch (Exception e) {
               Logging.error(e);
           }
        });
        super.run(consumerThread);
    }
}
