package kafka;

import influx.InfluxDAO;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;

public class PlanetConsumer2 extends AbstractConsumer {

    public PlanetConsumer2(){
        super("streams-planets-input", "http://localhost:8086", "PlanetConsumer2Test");
    }

    @Override
    public void consume() {
        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("streams-planets-input"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));

            for(ConsumerRecord<String, String> record : records){
                System.out.println(record.value());
            }
        }
    }
}
