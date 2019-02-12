package kafka.consumers;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import util.Config;
import util.Logging;

public class PiMessageConsumer extends GenericThreadedConsumer<String, String> {
    public PiMessageConsumer(){
        super("python-input", Config.getLocalBootstrapServersConfig(), "PiMessageConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
           try {
               
           } catch (Exception e) {
               Logging.error(e);
           }
        });
    }
}
