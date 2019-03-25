package kafka.test;

import com.google.gson.Gson;
import helldivers.Statistics;
import kafka.generic.consumers.GenericRunnableInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestHelldiversConsumer {

    public static void main(String[] args) {
        // old code
        //new HelldiversConsumer().run();

        // records processor for parsing statistics Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> statisticsToPointBatch = consumerRecords -> {

            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String, String> record : consumerRecords) {
                //
                List<Map> statisticsList = gson.fromJson(record.value(), List.class);

                for (Map statistics : statisticsList) {
                    // todo hacky fix, find different way
                    long timeStamp = (long) Math.round((double) statistics.get("timeStamp"));

                    statistics.put("timeStamp", timeStamp);

                    Statistics statisticsObject = new Statistics(statistics);
                    batch.add(statisticsObject.toPoint());
                }
            }

            return batch;
        };

        // records processor for parsing status Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> StatusesToPointBatch = consumerRecords -> {
            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String, String> record : consumerRecords) {
                //
                List<Map> campaignStatusList = gson.fromJson(record.value(), List.class);

                for (Map status : campaignStatusList) {
                    // todo hacky fix, find different way
                    long timeStamp = Double.valueOf(status.get("timeStamp")).longValue();

                    statistics.put("timeStamp", timeStamp);

                    Statistics statisticsObject = new Statistics(statistics);
                    batch.add(statisticsObject.toPoint());
                }
            }

            return batch;
        };

        new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-statistics", Config.getLocalBootstrapServersConfig(), "HelldiversConsumer",
                statisticsToPointBatch)
                .run();


    }
}