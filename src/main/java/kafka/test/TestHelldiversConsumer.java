package kafka.test;

import com.google.gson.Gson;
import helldivers.CampaignStatus;
import helldivers.Statistics;
import kafka.generic.consumers.GenericRunnableInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
                // JSON to objects
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
                // json to Object casting
                List<CampaignStatus> campaignStatuses = gson.fromJson(record.value(), List.class);

                // parsing objects
                for (CampaignStatus cstat: campaignStatuses) {
                    // parse campaign status object to Influx point
                    Point point = Point.measurement("helldivers-campaign-status")
                            .time(cstat.getTimeStamp(), TimeUnit.MILLISECONDS)
                            .tag("season", String.valueOf(cstat.getSeason()))
                            .tag("enemy", cstat.getEnemyName())
                            .tag("status", cstat.getStatus())
                            .tag("introduction_order", String.valueOf(cstat.getIntroductionOrder()))
                            .addField("points", cstat.getPoints())
                            .addField("points_max", cstat.getPointsMax())
                            .addField("points_taken", cstat.getPointsTaken())
                            .build();
                    // add to buffer
                    batch.add(point);
                }
            }

            // return the buffer so the InfluxDao can insert it into it's database
            return batch;
        };

        // consuming statistics
        new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-statistics", Config.getLocalBootstrapServersConfig(), "HelldiversStatisticsConsumer",
                statisticsToPointBatch)
                .run();

        // consuming campaign statuses
        new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-campaign-status", Config.getLocalBootstrapServersConfig(), "HelldiversCampaignStatusConsumer",
                StatusesToPointBatch)
                .run();


    }
}