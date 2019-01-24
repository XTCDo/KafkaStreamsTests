package kafka;

import influx.InfluxDAO;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.influxdb.dto.Point;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class PlanetConsumer {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "PlanetConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("streams-planets-input"));
        InfluxDAO dao = new InfluxDAO("http://localhost:8086");
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
            for(ConsumerRecord<String, String> record : records){
                Planet planet = new Planet(record.value());
                planet.describe();
                Point point = planetToPoint(planet);
                dao.writePoint("kafka_test", point);
            }
        }
    }

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
