package kafka.producers;

import kafka.generic.producers.GenericThreadedProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import planets.Planet;
import planets.PlanetProvider;
import util.Config;

import java.util.List;

public class PlanetVaryingTemperatureProducer extends GenericThreadedProducer<String, String> {
    public PlanetVaryingTemperatureProducer(){
        super("streams-planets-input", Config.getLocalBootstrapServersConfig());
    }

    public void run(){
        Thread producerThread = new Thread(() -> {
            try {
                while(true){
                    List<Planet> planets = PlanetProvider.getPlanetsVaryingTemperature();
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
