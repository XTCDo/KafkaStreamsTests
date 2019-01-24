package kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

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
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
            for(ConsumerRecord<String, String> record : records){
                Planet p = new Planet(record.value());
                p.describe();
            }
        }
    }
}
