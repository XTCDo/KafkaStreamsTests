package kafka.test;

import kafka.producers.NewPlanetProducer;
import util.Config;

public class TestNewPlanetProducer {
    public static void main(String[] args){
        new NewPlanetProducer("streams-planets-input", Config.getLocalBootstrapServersConfig()).run();
    }
}
