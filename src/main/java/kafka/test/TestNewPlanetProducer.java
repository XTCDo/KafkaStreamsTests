package kafka.test;

import kafka.producers.NewPlanetProducer;

public class TestNewPlanetProducer {
    public static void main(String[] args){
        new NewPlanetProducer("streams-planets-input", "localhost:9092").run();
    }
}
