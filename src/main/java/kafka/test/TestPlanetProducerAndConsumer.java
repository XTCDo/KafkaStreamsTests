package kafka.test;

import kafka.consumers.NewPlanetConsumer;
import kafka.consumers.PlanetInfluxConsumer;
import kafka.producers.PlanetVaryingTemperatureProducer;

public class TestPlanetProducerAndConsumer {
    public static void main(String[] args){
        new PlanetVaryingTemperatureProducer().run();
        new PlanetInfluxConsumer().run();
    }
}
