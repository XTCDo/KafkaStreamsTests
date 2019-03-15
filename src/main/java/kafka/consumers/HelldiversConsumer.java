package kafka.consumers;

import com.google.gson.Gson;
import helldivers.Statistics;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import javax.print.DocFlavor.STRING;
import kafka.generic.consumers.GenericThreadedConsumer;
import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;

public class HelldiversConsumer extends GenericThreadedInfluxConsumer {

    private static final String topic = "helldivers-statistics";
    private static final String TAG = "HelldiversConsumer";
    private static final String influxURL = "http://localhost:8086";
    public HelldiversConsumer() {
        super(influxURL, topic, Config.getLocalBootstrapServersConfig(), "Helldiversconsumer");
    }

    public void run() {
        Thread consumerThread = new Thread(() -> {
            Gson gson = new Gson();
            try {
                while (true) {
                    ConsumerRecords<String, String> records = getConsumer()
                        .poll(Duration.ofMillis(10));

                    for (ConsumerRecord<String, String> record : records) {
                        List<Map> statisticsList = gson.fromJson(record.value(), List.class);
                        for (Map statistics : statisticsList) {
                            // todo hacky fix, find different way
                            long timeStamp = (long) Math.round((double) statistics.get("timeStamp"));
                            statistics.put("timeStamp", timeStamp);
                            Statistics statisticsObject = new Statistics(statistics);
                            Logging.log(statisticsObject.getDescription(), TAG);
                            Point point = statisticsObject.toPoint();
                            getInfluxDAO().writePoint("HELLDIVERS", point);
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
