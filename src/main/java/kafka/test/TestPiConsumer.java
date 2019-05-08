package kafka.test;

import kafka.consumers.PiMessageConsumer;

public class TestPiConsumer {
    public static void main(String[] args) {
        new PiMessageConsumer().run();
    }
}
