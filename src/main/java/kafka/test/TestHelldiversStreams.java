package kafka.test;

import helldivers.HelldiversAPIWrapper;
import kafka.producers.HelldiversDataProducer;

public class TestHelldiversStreams {
    public static void main(String args[]){
        new HelldiversDataProducer().run();
    }
}
