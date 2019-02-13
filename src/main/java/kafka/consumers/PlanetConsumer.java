package kafka.consumers;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import planets.Planet;
import util.Config;

import java.time.Duration;

public class PlanetConsumer extends GenericThreadedConsumer<String, String> {
    public PlanetConsumer(){
        super("streams-planets-input", Config.getLocalBootstrapServersConfig(), "PlanetConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            try {
                while(true){
                    ConsumerRecords<String,String> records = getConsumer().poll(Duration.ofMillis(10));

                    for(ConsumerRecord<String, String> record : records){
                        Planet planet = new Planet(record.value());
                        planet.describe();
                        System.out.println("");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        super.run(consumerThread);
    }
}