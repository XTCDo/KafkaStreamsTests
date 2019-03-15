package kafka.consumers;

import com.google.gson.Gson;
import helldivers.Statistics;
import java.time.Duration;
import java.util.List;
import java.util.Map;
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
            Gson gson = new Gson();
            try {
                while (true) {
                    ConsumerRecords<String, String> records = getConsumer()
                        .poll(Duration.ofMillis(10));

                    for (ConsumerRecord<String, String> record : records) {
                        //Logging.log(record.value(), TAG);
                        List<Map> statisticsList = gson.fromJson(record.value().toString(), List.class);
                        for (Map statistics : statisticsList) {
                            Statistics statisticsObject = new Statistics(statistics);
                            Logging.log(statisticsObject.getDescription(), TAG);
                        }
                    }
                }
            } catch (Exception e) {
                Logging.error(e, TAG);
            }
        });

        super.run(consumerThread);
    }
}
