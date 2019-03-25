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
import java.util.function.Function;

public class TestHelldiversConsumer {

    public static void main(String[] args) {
        // records processor for parsing statistics Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> StatisticsToPointBatch = consumerRecords -> {

            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String, String> record : consumerRecords) {
                // JSON to object casting
                List<Map> statisticsList = gson.fromJson(record.value(), List.class);
                //parsing objects to correct type and inserting to Influx
                statisticsList.forEach(statistics ->
                        batch.add(new Statistics(statistics)
                                .toPoint("helldivers-statistics"))
                );

            }

            return batch;
        };

        // records processor for parsing status Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> StatusesToPointBatch = consumerRecords -> {
            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String, String> record : consumerRecords) {
                // json to Object casting
                List<Map> campaignStatuses = gson.fromJson(record.value(), List.class);
                //parsing objects to correct type and inserting to Influx
                campaignStatuses.forEach(campaignStatus ->
                        batch.add(new CampaignStatus(campaignStatus)
                                .toPoint("helldivers-campaign-status")));
            }
            return batch;
        };

        // records processor for parsing attack events Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> AttackEventsToPointBatch = consumerRecords -> {
            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String,String> record: consumerRecords){
                // JSON to object casting
                List<Map> attackEvents = gson.fromJson(record.value(), List.class);
                // process to influx
                attackEvents.forEach(attackEvent ->
                        batch.add(new CampaignStatus(attackEvent)
                                .toPoint("helldivers-attack-events")));
            }
            return batch;
        };

        // records processor for parsing defend events Records to Influx Points
        Function<ConsumerRecords<String, String>, List<Point>> DefendEventsToPointBatch = consumerRecords -> {
            Gson gson = new Gson();
            List<Point> batch = new ArrayList<>();

            for (ConsumerRecord<String,String> record: consumerRecords){
                // JSON to object casting
                List<Map> defendEvents = gson.fromJson(record.value(), List.class);
                // process to influx
                defendEvents.forEach(defendEvent ->
                        batch.add(new CampaignStatus(defendEvent)
                                .toPoint("helldivers-defend-events")));
            }
            return batch;
        };


        // consuming statistics
        Thread statisticsConsumer = new Thread( new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-statistics", Config.getLocalBootstrapServersConfig(), "HelldiversStatisticsConsumer",
                StatisticsToPointBatch));

        // consuming campaign statuses
        Thread campaignStatusConsumer = new Thread( new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-campaign_status", Config.getLocalBootstrapServersConfig(), "HelldiversCampaignStatusConsumer",
                StatusesToPointBatch));

        // consuming attack events
        Thread attackEventsConsumer = new Thread(  new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-attack_events", Config.getLocalBootstrapServersConfig(), "HelldiversAttackEventConsumer",
                AttackEventsToPointBatch)
        );

        // consuming defend events
        Thread defendEventsConsumer = new Thread( new GenericRunnableInfluxConsumer(
                "http://localhost:8086", "HELLDIVERS",
                "helldivers-defend_events", Config.getLocalBootstrapServersConfig(), "HelldiversDefendEventConsumer",
                DefendEventsToPointBatch)
        );

        statisticsConsumer.start();
        campaignStatusConsumer.start();
        attackEventsConsumer.start();
        defendEventsConsumer.start();

    }
}