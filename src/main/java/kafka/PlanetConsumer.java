package kafka;

import influx.InfluxDAO;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.influxdb.dto.Point;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PlanetConsumer {
    public static void main(String[] args){
        // Set properties
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "PlanetConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Create consumer
        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        // Subscribe to planets input topic
        consumer.subscribe(Arrays.asList("streams-planets-input"));

        // Get dao object
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");

        Thread consumerThread = new Thread(() -> {
            try {
                // Keep reading records for all eternity
                while(true){
                    // Poll for records
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));

                    // Turn every record into a Planet, describe it and
                    // write it to Influx
                    for(ConsumerRecord<String, String> record : records){
                        Planet planet = new Planet(record.value());
                        planet.describe();
                        Point point = planetToPoint(planet);
                        dao.writePoint("kafka_test", point);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                dao.close();
            }
        });

        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("consumer-shutdown-hook"){
            @Override
            public void run(){
                consumer.close();
                dao.close();
                latch.countDown();
            }
        });
    }

    /**
     * Turns planets into Points
     * @param planet The Planet to turn into a Point
     * @return The Planet turned into a Point
     */
    private static Point planetToPoint(Planet planet){
        Point point = Point.measurement("planets")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name", planet.getName())
                .addField("capitol", planet.getCapitol())
                .addField("color", planet.getColor())
                .addField("gravity", planet.getGravity())
                .addField("dist_to_sun", planet.getDistanceToSun())
                .addField("temperature", planet.getTemperature())
                .build();

        return point;
    }
}
