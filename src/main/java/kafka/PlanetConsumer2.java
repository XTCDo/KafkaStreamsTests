package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;

public class PlanetConsumer2 extends AbstractConsumer {

    public PlanetConsumer2(){
        super("streams-planets-input", "PlanetConsumer2Test");
    }

    @Override
    public void consume() {
        final Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));

            for(ConsumerRecord<String, String> record : records){
                System.out.println(record.value());
            }
        }
    }

    @Override
    public void handleException(Exception e) {
        e.printStackTrace();
    }
}
