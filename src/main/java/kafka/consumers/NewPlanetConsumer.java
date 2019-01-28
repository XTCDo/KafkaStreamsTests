package kafka.consumers;

import kafka.generic.consumers.GenericThreadedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import planets.Planet;

import java.time.Duration;

public class NewPlanetConsumer extends GenericThreadedConsumer<String, String> {
    public NewPlanetConsumer(){
        super("streams-planets-input", "localhost:9092", "NewPlanetConsumer");
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