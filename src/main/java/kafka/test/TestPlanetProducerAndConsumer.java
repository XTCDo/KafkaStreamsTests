package kafka.test;

import kafka.consumers.NewPlanetConsumer;
import kafka.producers.PlanetVaryingTemperatureProducer;

public class TestPlanetProducerAndConsumer {
    public static void main(String[] args){
        new PlanetVaryingTemperatureProducer().run();
        new NewPlanetConsumer().run();
    }
}
