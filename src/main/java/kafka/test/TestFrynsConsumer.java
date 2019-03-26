package kafka.test;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import kafka.generic.consumers.GenericRunnableInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;
import util.Logging;
import util.MapUtils;

public class TestFrynsConsumer {
    private final String TAG = "TestFrynsConsumer";
    public static void main(String[] args) {
        Function<ConsumerRecords<String, String>, List<Point>> frynsDataToPointBatch = consumerRecords -> {
            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();
            for (ConsumerRecord<String, String> record : consumerRecords) {
                Map recordAsMap = gson.fromJson(record.value(), Map.class);
                Point point = Point.measurement("fryns_data")
                    .tag("name", (String) ((Map) recordAsMap.get("tags")).get("name"))
                    .time(((Double) recordAsMap.get("time")).longValue(), TimeUnit.MILLISECONDS)
                    .addField("temperatuurSensor1", Double.parseDouble((String) ((Map) recordAsMap.get("fields")).get("temperatuurSensor1")))
                    .build();
                batch.add(MapUtils.influxMapToPoint(recordAsMap, "fryns_data"));
                Logging.debug(recordAsMap.toString());
            }

            return batch;
        };

        new GenericRunnableInfluxConsumer(
            "http://localhost:8086", "fryns",
            "fryns-input", Config.getLocalBootstrapServersConfig(), "Fryns",
            frynsDataToPointBatch
        ).run();
    }
}
