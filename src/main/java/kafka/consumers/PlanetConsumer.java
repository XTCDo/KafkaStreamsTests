package kafka.consumers;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import planets.Planet;
import util.Config;

import java.time.Duration;

public class PlanetConsumer extends GenericThreadedConsumer<String, String> {
    final static String topic = "streams-planets-input";

    public PlanetConsumer() {
        super(topic, Config.getLocalBootstrapServersConfig(), "PlanetConsumer");
    }

    public void run() {
        Thread consumerThread = new Thread(() -> {
            try {
                while(true) {
                    // Get records containing Strings that represent Planets
                    ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));

                    // Turn every String into a Planet and describe it
                    for(ConsumerRecord<String, String> record : records) {
                        Planet planet = new Planet(record.value());
                        planet.describe();
                        System.out.println();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        super.run(consumerThread);
    }
}
