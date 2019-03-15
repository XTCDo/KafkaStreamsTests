package kafka.consumers;

import java.time.Duration;
import javax.print.DocFlavor.STRING;
import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Config;
import util.Logging;

public class HelldiversConsumer extends GenericThreadedConsumer {

    private static final String topic = "helldivers-statistics";
    private static final String TAG = "HelldiversConsumer";

    public HelldiversConsumer() {
        super(topic, Config.getLocalBootstrapServersConfig(), "Helldiversconsumer");
    }

    public void run() {
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = getConsumer()
                        .poll(Duration.ofMillis(10));

                    for (ConsumerRecord<String, String> record : records) {
                        Logging.log(record.value(), TAG);
                    }
                }
            } catch (Exception e) {
                Logging.error(e, TAG);
            }
        });

        super.run(consumerThread);
    }
}
