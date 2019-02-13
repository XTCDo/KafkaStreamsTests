package kafka.consumers;

import kafka.generic.consumers.GenericThreadedInfluxConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.influxdb.dto.Point;
import planets.Planet;
import util.Config;

import java.time.Duration;

public class PlanetInfluxConsumer extends GenericThreadedInfluxConsumer<String, String> {
    public PlanetInfluxConsumer(){
        super("http://localhost:8086", "streams-planets-input",
                Config.getLocalBootstrapServersConfig(), "PlanetInfluxConsumer");
    }

    public void run(){
        Thread consumerThread = new Thread(() -> {
            try {
                while(true){
                    // Get records containing Strings describing Planets
                    ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofMillis(10));

                    // Turn every record into a Planet, describe that planet
                    // and then turn it into a point to put in InfluxDB
                    for(ConsumerRecord<String, String> record : records){
                        Planet planet = new Planet(record.value());
                        planet.describe();
                        Point point = planet.toPoint();
                        getInfluxDAO().writePoint("kafka_test", point);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                getInfluxDAO().close();
            }
        });
        super.run(consumerThread);
    }
}
