package kafka.producers;

import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import planets.Planet;
import planets.PlanetProvider;
import util.Config;

import java.util.List;

public class PlanetVaryingTemperatureProducer extends GenericThreadedProducer<String, String> {
    private static final String topic = "streams-planets-input";

    public PlanetVaryingTemperatureProducer(){
        super(topic, Config.getLocalBootstrapServersConfig());
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            try {
                while(true){
                    // PlanetProvider provides a list of PlanetVaryingTemperature(s)
                    List<Planet> planets = PlanetProvider.getPlanetsVaryingTemperature();

                    // Send these planets to Kafka
                    planets.forEach(planet -> getProducer().send(new ProducerRecord<String, String>(getTopic(),
                            planet.getName(), planet.toString())));
                    Thread.sleep(100);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        super.run(producerThread);
    }
}
