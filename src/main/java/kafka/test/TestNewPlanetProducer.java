package kafka.test;

import kafka.producers.PlanetProducer;
import util.Config;

public class TestNewPlanetProducer {
    public static void main(String[] args){
        new PlanetProducer("streams-planets-input", Config.getLocalBootstrapServersConfig()).run();
    }
}
